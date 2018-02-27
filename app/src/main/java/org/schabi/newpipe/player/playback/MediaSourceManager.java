package org.schabi.newpipe.player.playback;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.schabi.newpipe.extractor.stream.StreamInfo;
import org.schabi.newpipe.playlist.PlayQueue;
import org.schabi.newpipe.playlist.PlayQueueItem;
import org.schabi.newpipe.playlist.events.MoveEvent;
import org.schabi.newpipe.playlist.events.PlayQueueEvent;
import org.schabi.newpipe.playlist.events.RemoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import Assignment4.CodeCoverage;

public class MediaSourceManager {
    private final String TAG = "MediaSourceManager@" + Integer.toHexString(hashCode());
    // One-side rolling window size for default loading
    // Effectively loads windowSize * 2 + 1 streams per call to load, must be greater than 0
    private final int windowSize;
    private final PlaybackListener playbackListener;
    private final PlayQueue playQueue;

    // Process only the last load order when receiving a stream of load orders (lessens I/O)
    // The higher it is, the less loading occurs during rapid noncritical timeline changes
    // Not recommended to go below 100ms
    private final long loadDebounceMillis;
    private final PublishSubject<Long> debouncedLoadSignal;
    private final Disposable debouncedLoader;

    private final DeferredMediaSource.Callback sourceBuilder;

    private DynamicConcatenatingMediaSource sources;

    private Subscription playQueueReactor;
    private SerialDisposable syncReactor;

    private PlayQueueItem syncedItem;

    private boolean isBlocked;

    public MediaSourceManager(@NonNull final PlaybackListener listener,
                              @NonNull final PlayQueue playQueue) {
        this(listener, playQueue, 1, 400L);
    }

    public MediaSourceManager(@NonNull final PlaybackListener listener,
                               @NonNull final PlayQueue playQueue,
                               final int windowSize,
                               final long loadDebounceMillis) {
        if (windowSize <= 0) {
            throw new UnsupportedOperationException("MediaSourceManager window size must be greater than 0");
        }

        this.playbackListener = listener;
        this.playQueue = playQueue;
        this.windowSize = windowSize;
        this.loadDebounceMillis = loadDebounceMillis;

        this.syncReactor = new SerialDisposable();
        this.debouncedLoadSignal = PublishSubject.create();
        this.debouncedLoader = getDebouncedLoader();

        this.sourceBuilder = getSourceBuilder();

        this.sources = new DynamicConcatenatingMediaSource();

        playQueue.getBroadcastReceiver()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReactor());
    }

    /*//////////////////////////////////////////////////////////////////////////
    // DeferredMediaSource listener
    //////////////////////////////////////////////////////////////////////////*/

    private DeferredMediaSource.Callback getSourceBuilder() {
        return playbackListener::sourceOf;
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Exposed Methods
    //////////////////////////////////////////////////////////////////////////*/
    /**
     * Dispose the manager and releases all message buses and loaders.
     * */
    public void dispose() {
        if (debouncedLoadSignal != null) debouncedLoadSignal.onComplete();
        if (debouncedLoader != null) debouncedLoader.dispose();
        if (playQueueReactor != null) playQueueReactor.cancel();
        if (syncReactor != null) syncReactor.dispose();
        if (sources != null) sources.releaseSource();

        playQueueReactor = null;
        syncReactor = null;
        syncedItem = null;
        sources = null;
    }

    /**
     * Loads the current playing stream and the streams within its windowSize bound.
     *
     * Unblocks the player once the item at the current index is loaded.
     * */
    public void load() {
        loadDebounced();
    }

    /**
     * Blocks the player and repopulate the sources.
     *
     * Does not ensure the player is unblocked and should be done explicitly through {@link #load() load}.
     * */
    public void reset() {
        tryBlock();

        syncedItem = null;
        populateSources();
    }
    /*//////////////////////////////////////////////////////////////////////////
    // Event Reactor
    //////////////////////////////////////////////////////////////////////////*/

    private Subscriber<PlayQueueEvent> getReactor() {
        return new Subscriber<PlayQueueEvent>() {
            @Override
            public void onSubscribe(@NonNull Subscription d) {
                if (playQueueReactor != null) playQueueReactor.cancel();
                playQueueReactor = d;
                playQueueReactor.request(1);
            }

            @Override
            public void onNext(@NonNull PlayQueueEvent playQueueMessage) {
                if (playQueueReactor != null) onPlayQueueChanged(playQueueMessage);
            }

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onComplete() {}
        };
    }

    /*
     * Requirements:
     * - If the PlayQueue is empty and complete, the PlayBackListener should be shutdown.
     * - If the given event is INIT, REORDER or ERROR then reset queue
     *   If APPEND populate the sources
      *  If REMOVE remove the Index specified by the event
     *   If MOVE, move the index specified by the event
     *   Else do nothing
     * - Load differently depending on the event  (low or high frequency)
     * - If the PlayQueue is not ready fetch it
     * - If subscribed request the next one
     */
    public void onPlayQueueChanged(final PlayQueueEvent event, Assignment4.CodeCoverage... codeCoverage) {
        CodeCoverage cc = codeCoverage.length != 0 ? codeCoverage[0] : new CodeCoverage("onPlayQueueChanged");
        String data = "event: " + event.toString();

        if(playQueue.isEmpty()) { // 0
            cc.visitBranch(0, data);
        }


        if (playQueue.isEmpty() && playQueue.isComplete()) { // branch 0 && 1
            cc.visitBranch(1, data);
            playbackListener.shutdown();
            return;
        }
        else { // branch 2
            cc.visitBranch(2, data);
        }

        // Event specific action
        switch (event.type()) {
            case INIT: cc.visitBranch(3, data);// 3
            case REORDER: cc.visitBranch(4, data);// 4
            case ERROR: cc.visitBranch(5, data);// 5
                reset();
                break;
            case APPEND: cc.visitBranch(6, data);// 6
                populateSources();
                break;
            case REMOVE: cc.visitBranch(7, data);// 7
                final RemoveEvent removeEvent = (RemoveEvent) event;
                remove(removeEvent.getRemoveIndex());
                break;
            case MOVE: cc.visitBranch(8, data); // 8
                final MoveEvent moveEvent = (MoveEvent) event;
                move(moveEvent.getFromIndex(), moveEvent.getToIndex());
                break;
            case SELECT: cc.visitBranch(9, data); // 9
            case RECOVERY: cc.visitBranch(10, data); // 10
            default:
                cc.visitBranch(11, data);// 11
                break;
        }

        // Loading and Syncing
        switch (event.type()) {
            case INIT: cc.visitBranch(12, data); // 12
            case REORDER: cc.visitBranch(13, data); // 13
            case ERROR:
                cc.visitBranch(14, data); // 14
                loadImmediate(); // low frequency, critical events
                break;
            case APPEND: cc.visitBranch(15, data); // 15
            case REMOVE: cc.visitBranch(16, data); // 16
            case SELECT: cc.visitBranch(17, data); // 17
            case MOVE: cc.visitBranch(18, data); // 18
            case RECOVERY: cc.visitBranch(19, data); // 19
            default:
                cc.visitBranch(20, data); // 20
                loadDebounced(); // high frequency or noncritical events
                break;
        }

        if (!isPlayQueueReady()) { // 21
            cc.visitBranch(21, data);
            tryBlock();
            playQueue.fetch();
        }

        else { // 22
            cc.visitBranch(22, data);
        }

        if (playQueueReactor != null) {
            cc.visitBranch(23, data); // 23
            playQueueReactor.request(1);
        }
        else { // 24
            cc.visitBranch(24, data);
        }
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Internal Helpers
    //////////////////////////////////////////////////////////////////////////*/

    public boolean isPlayQueueReady() {
        return playQueue.isComplete() || playQueue.size() - playQueue.getIndex() > windowSize;
    }

    private boolean tryBlock() {
        if (!isBlocked) {
            playbackListener.block();
            resetSources();
            isBlocked = true;
            return true;
        }
        return false;
    }

    private boolean tryUnblock() {
        if (isPlayQueueReady() && isBlocked && sources != null) {
            isBlocked = false;
            playbackListener.unblock(sources);
            return true;
        }
        return false;
    }

    private void sync() {
        final PlayQueueItem currentItem = playQueue.getItem();
        if (currentItem == null) return;

        final Consumer<StreamInfo> onSuccess = info -> syncInternal(currentItem, info);
        final Consumer<Throwable> onError = throwable -> {
            Log.e(TAG, "Sync error:", throwable);
            syncInternal(currentItem, null);
        };

        if (syncedItem != currentItem) {
            syncedItem = currentItem;
            final Disposable sync = currentItem.getStream()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onSuccess, onError);
            syncReactor.set(sync);
        }
    }

    private void syncInternal(@android.support.annotation.NonNull final PlayQueueItem item,
                              @Nullable final StreamInfo info) {
        if (playQueue == null || playbackListener == null) return;
        // Ensure the current item is up to date with the play queue
        if (playQueue.getItem() == item && playQueue.getItem() == syncedItem) {
            playbackListener.sync(syncedItem,info);
        }
    }

    private void loadDebounced() {
        debouncedLoadSignal.onNext(System.currentTimeMillis());
    }

    private void loadImmediate() {
        // The current item has higher priority
        final int currentIndex = playQueue.getIndex();
        final PlayQueueItem currentItem = playQueue.getItem(currentIndex);
        if (currentItem == null) return;
        loadItem(currentItem);

        // The rest are just for seamless playback
        final int leftBound = Math.max(0, currentIndex - windowSize);
        final int rightLimit = currentIndex + windowSize + 1;
        final int rightBound = Math.min(playQueue.size(), rightLimit);
        final List<PlayQueueItem> items = new ArrayList<>(playQueue.getStreams().subList(leftBound, rightBound));

        // Do a round robin
        final int excess = rightLimit - playQueue.size();
        if (excess >= 0) items.addAll(playQueue.getStreams().subList(0, Math.min(playQueue.size(), excess)));

        for (final PlayQueueItem item: items) loadItem(item);
    }

    private void loadItem(@Nullable final PlayQueueItem item) {
        if (item == null) return;

        final int index = playQueue.indexOf(item);
        if (index > sources.getSize() - 1) return;

        final DeferredMediaSource mediaSource = (DeferredMediaSource) sources.getMediaSource(playQueue.indexOf(item));
        if (mediaSource.state() == DeferredMediaSource.STATE_PREPARED) mediaSource.load();

        tryUnblock();
        if (!isBlocked) sync();
    }

    private void resetSources() {
        if (this.sources != null) this.sources.releaseSource();
        this.sources = new DynamicConcatenatingMediaSource();
    }

    private void populateSources() {
        if (sources == null) return;

        for (final PlayQueueItem item : playQueue.getStreams()) {
            insert(playQueue.indexOf(item), new DeferredMediaSource(item, sourceBuilder));
        }
    }

    private Disposable getDebouncedLoader() {
        return debouncedLoadSignal
                .debounce(loadDebounceMillis, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timestamp -> loadImmediate());
    }
    /*//////////////////////////////////////////////////////////////////////////
    // Media Source List Manipulation
    //////////////////////////////////////////////////////////////////////////*/

    /**
     * Inserts a source into {@link DynamicConcatenatingMediaSource} with position
     * in respect to the play queue.
     *
     * If the play queue index already exists, then the insert is ignored.
     * */
    private void insert(final int queueIndex, final DeferredMediaSource source) {
        if (sources == null) return;
        if (queueIndex < 0 || queueIndex < sources.getSize()) return;

        sources.addMediaSource(queueIndex, source);
    }

    /**
     * Removes a source from {@link DynamicConcatenatingMediaSource} with the given play queue index.
     *
     * If the play queue index does not exist, the removal is ignored.
     * */
    private void remove(final int queueIndex) {
        if (sources == null) return;
        if (queueIndex < 0 || queueIndex > sources.getSize()) return;

        sources.removeMediaSource(queueIndex);
    }

    private void move(final int source, final int target) {
        if (sources == null) return;
        if (source < 0 || target < 0) return;
        if (source >= sources.getSize() || target >= sources.getSize()) return;

        sources.moveMediaSource(source, target);
    }
}
