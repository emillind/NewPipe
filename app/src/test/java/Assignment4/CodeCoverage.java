package Assignment4;

/**
 * Created by emillindblom on 2018-02-25.
 */

public class CodeCoverage {

    private boolean[] branches;
    private String methodName;
    private String[] data;

    CodeCoverage(String methodName) {
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
            case "onClick":
                return 0;
            case "initSearchListeners":
                return 0;
            case "initSuggestionObserver":
                return 0;
            case "onPlayQueueChanged":
                return 0;
            case "onScroll":
                return 0;
            case "animateView":
                return 0;
            case "tryToSave":
                return 0;
            case "getResultHandler":
                return 0;
            case "run":
                return 0;
            case "main":
                return 0;
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
        for (int i = 0; i < data.length; i++) {
            if (branches[i]) {
                sb.append("Branch " + i + " reached with data: " + data[i] + "\n");
            } else {
                sb.append("Branch " + i + " was not reached.\n");
            }
        }
        return sb.toString();
    }

}
