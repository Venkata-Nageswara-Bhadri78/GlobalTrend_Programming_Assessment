import java.util.Random;
public class KthLargestElement {
    public int findKthLargest(int[] nums, int k) {
        // We need to find the (n-k)th smallest element
        int n = nums.length;
        int targetIndex = n - k; // (n-k)th smallest element index
        // Apply quick select algorithm
        return quickSelect(nums, 0, n - 1, targetIndex);
}
    private int quickSelect(int[] nums, int left, int right, int k) {
        // Randomly select pivot to avoid worst-case O(n^2) behavior
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        int pivot = nums[pivotIndex];
        // Move pivot to end
        swap(nums, pivotIndex, right);
        // Partition
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i++, j);
            }
        }
        swap(nums, i, right); // Move pivot to its final position
        // Recursively select
        if (i == k) {
            return nums[i];
        } else if (i < k) {
            return quickSelect(nums, i + 1, right, k);
        } else {
            return quickSelect(nums, left, i - 1, k);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static void main(String[] args) {
        KthLargestElement solution = new KthLargestElement();
        // Example usage:
        int[] nums = {3, 2, 1, 5, 6, 4}; // Given array
        int k = 2; // kth largest element we want to find
        int result = solution.findKthLargest(nums, k);
        System.out.println("Kth largest element: " + result); // Expected output: 5
    }
}
