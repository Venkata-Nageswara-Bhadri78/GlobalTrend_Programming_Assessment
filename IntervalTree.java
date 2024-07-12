class Interval {
    int start;
    int end;
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
import java.util.ArrayList;
import java.util.List;

public class IntervalTree {

    private static class IntervalNode {
        Interval interval;
        int maxEnd;
        IntervalNode left;
        IntervalNode right;

        public IntervalNode(Interval interval) {
            this.interval = interval;
            this.maxEnd = interval.end;
            this.left = null;
            this.right = null;
        }
    }

    private IntervalNode root;

    public IntervalTree() {
        this.root = null;
    }

    public void insertInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        root = insertInterval(root, interval);
    }

    private IntervalNode insertInterval(IntervalNode node, Interval interval) {
        if (node == null) {
            return new IntervalNode(interval);
        }

        // Insert into left subtree
        if (interval.start < node.interval.start) {
            node.left = insertInterval(node.left, interval);
        }
        // Insert into right subtree
        else {
            node.right = insertInterval(node.right, interval);
        }

        // Update maxEnd in the current node
        if (node.maxEnd < interval.end) {
            node.maxEnd = interval.end;
        }

        return node;
    }

    public void deleteInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        root = deleteInterval(root, interval);
    }

    private IntervalNode deleteInterval(IntervalNode node, Interval interval) {
        if (node == null) {
            return null;
        }

        // If current interval overlaps with the given interval, delete it
        if (doOverlap(node.interval, interval)) {
            return deleteAndReplace(node, interval);
        }

        // Go to left subtree if needed
        if (node.left != null && node.left.maxEnd >= interval.start) {
            node.left = deleteInterval(node.left, interval);
        }

        // Go to right subtree if needed
        node.right = deleteInterval(node.right, interval);

        // Update maxEnd in the current node
        node.maxEnd = Math.max(getMaxEnd(node.left), getMaxEnd(node.right));

        return node;
    }

    private IntervalNode deleteAndReplace(IntervalNode node, Interval interval) {
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            // Find minimum node in the right subtree (or maximum in the left subtree)
            IntervalNode minRight = findMinNode(node.right);
            node.interval = minRight.interval;
            node.right = deleteInterval(node.right, minRight.interval);
        }

        // Update maxEnd in the current node
        node.maxEnd = Math.max(getMaxEnd(node.left), getMaxEnd(node.right));

        return node;
    }

    private IntervalNode findMinNode(IntervalNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private int getMaxEnd(IntervalNode node) {
        return (node == null) ? Integer.MIN_VALUE : node.maxEnd;
    }

    public List<Interval> findOverlappingIntervals(int start, int end) {
        List<Interval> result = new ArrayList<>();
        findOverlappingIntervals(root, start, end, result);
        return result;
    }

    private void findOverlappingIntervals(IntervalNode node, int start, int end, List<Interval> result) {
        if (node == null) {
            return;
        }

        // If current interval overlaps with the given interval
        if (doOverlap(node.interval, new Interval(start, end))) {
            result.add(node.interval);
        }

        // If left child's maxEnd is greater than start, search left subtree
        if (node.left != null && node.left.maxEnd >= start) {
            findOverlappingIntervals(node.left, start, end, result);
        }

        // Search right subtree
        findOverlappingIntervals(node.right, start, end, result);
    }

    private boolean doOverlap(Interval interval1, Interval interval2) {
        return !(interval1.end < interval2.start || interval2.end < interval1.start);
    }

    public static void main(String[] args) {
        IntervalTree intervalTree = new IntervalTree();

        // Insert intervals
        intervalTree.insertInterval(15, 20);
        intervalTree.insertInterval(10, 30);
        intervalTree.insertInterval(5, 15);
        intervalTree.insertInterval(17, 19);
        intervalTree.insertInterval(12, 15);
        intervalTree.insertInterval(30, 40);

        // Find overlapping intervals with [14, 16]
        List<Interval> overlappingIntervals = intervalTree.findOverlappingIntervals(14, 16);
        System.out.println("Overlapping intervals with [14, 16]:");
        for (Interval interval : overlappingIntervals) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }

        // Delete interval [12, 15]
        intervalTree.deleteInterval(12, 15);

        // Find overlapping intervals with [14, 16] after deletion
        overlappingIntervals = intervalTree.findOverlappingIntervals(14, 16);
        System.out.println("Overlapping intervals with [14, 16] after deletion:");
        for (Interval interval : overlappingIntervals) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
    }
}
