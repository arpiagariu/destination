package leetcode19.bitmanipulation;
/**
 * 477. The Hamming distance between two integers is the number of positions at which the corresponding bits are different.

Now your job is to find the total Hamming distance between all pairs of the given numbers.

Example:
Input: 4, 14, 2

Output: 6

Explanation: In binary representation, the 4 is 0100, 14 is 1110, and 2 is 0010 (just
showing the four bits relevant in this case). So the answer will be:
HammingDistance(4, 14) + HammingDistance(4, 2) + HammingDistance(14, 2) = 2 + 2 + 2 = 6.
Note:
Elements of the given array are in the range of 0 to 10^9
Length of the array will not exceed 10^4.
 */
public class TotalHammingDistance {

    // https://discuss.leetcode.com/topic/72104/java-solution-with-explanation
    /*
    We can separate the calculation to do one bit at a time. For example, look at the rightmost bit of all the numbers in nums. Suppose that i numbers have a rightmost 0-bit, and j numbers have a 1-bit. Then out of the pairs, i * j of them will have 1 in the rightmost bit of the XOR. This is because there are i * j ways to choose one number that has a 0-bit and one that has a 1-bit. These bits will therefore contribute i * j towards the total of all the XORs.

    Apply above algorithm to each bit (31 bits in total as specified in the problem), sum them together then we get the answer.
    */
    public int totalHammingDistance(int[] nums) {
        int n = 31;
        int len = nums.length;
        
        int[] countOfOnes = new int[n];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < n; j++) {
                if (((nums[i] >> j) & 1) == 1) {
                    countOfOnes[j]++;
                }
            }
        }
        
        int sum = 0;
        for (int count : countOfOnes) {
            sum += count * (len - count);
        }
        
        return sum;
    }
    
    public int totalHammingDistanceTLE(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                res += hammingDistance(nums[i], nums[j]);
            }
        }
        
        return res;
    }
    
    public int hammingDistance(int x, int y) {
        int z = x ^ y;
        
        int count = 0;
        while (z != 0) {
            count += (z & 1);
            z = z >> 1;
        }
        
        return count;
    }

}
