package Assignment4;

/**
 * Created by emillindblom on 2018-02-25.
 */

public class CodeCoverage {

    private boolean[] branches;
    private String methodName;
    private String[] data;

    public CodeCoverage(String methodName) {
        this.methodName = methodName;
        int branchCount = getBranchCount(methodName);
        this.branches = new boolean[branchCount];
        this.data = new String[branchCount];
    }

    /**
     * Returns the amount of branches for a method
     * @param methodName, name of the method
     * @return amount of branches
     */
    private int getBranchCount(String methodName) {
        switch (methodName) {
            case "initSearchListeners":
                return 13;
            case "initSuggestionObserver":
                return 16;
            case "getFileType":
                return 12;
            case "onPlayQueueChanged":
                return 25;
            case "onScroll":
                return 0;
            case "animateView":
                return 14;
            case "tryToSave":
                return 21;
            case "getResultHandler":
                return 0;
            case "getHighestQualityAudioIndex":
                return 15;
            case "main":
                return 0;
            case "getSortedStreamVideosList":
                return 20;
            default:
                return -1;
        }
    }

    /**
     * Marks a branch as visited as well as provides data for the reaching of that branch
     * @param id, The ID of the branch
     * @param data, The data used to reach the branch
     */
    public void visitBranch(int id, String data) {
        this.branches[id] = true;
        this.data[id] = data;
    }

    /**
     * Converts the Code Coverage to a string.
     * @return the code coverage.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        StringBuffer unvisited = new StringBuffer();
        double branchesVisited = 0;
        boolean allBranchesVisited = true;
        for (int i = 0; i < data.length; i++) {
            if (branches[i]) {
                branchesVisited++;
                sb.append("Branch " + i + " reached with data: " + data[i] + "\n");
            } else {
                allBranchesVisited = false;
                unvisited.append(i + ", ");
                sb.append("Branch " + i + " was not reached.\n");
            }
        }
        double coverage = branchesVisited/data.length;
        sb.append("==================================\nTotal branch coverage for " + methodName + ": "
                + 100*coverage + "%\n" + (allBranchesVisited ? "" : "Unreached branches: " + unvisited.toString() + "\n")
                + "==================================");
        return sb.toString();
    }

}
