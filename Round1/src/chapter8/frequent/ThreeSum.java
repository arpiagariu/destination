package chapter8.frequent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 15. Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note: The solution set must not contain duplicate triplets.

For example, given array S = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
 * @author Lei
 *
 */
public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Arrays.sort(nums); // First sort
        // 先固定一个数，之后另外两个数用2 Sum的思想
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip all duplicate elements
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    
                    res.add(list);
                    
                    left++;
                    right--;
                    
                    // Skip all duplicate elements
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return res;
    }
    
    // What if sorting is not allowed
    /**
     * Since it does not allow to sort the array, I assume it only asks for one triplet as solution or returns an empty triplet if no such sum exists. 
     * Also, I don't think using Hash map to store value to index works since the given array could have duplicates. 
     * I would recommend to use Hash map to store value to frequency.
     * 
     * 关键是怎么避免重复，楼主可以尝试用map[nums[i], count]记录frequency，然后把map里所有的key放在一个vector里，重复的会连在一起，这样就可以避免重复了
     * 
     * 我想到一个解法  可以用string来编码三个点 比如"1#5#6" 从小到大. 所有的解对应的string 放进set 就可以避免重复了
     */
    public List<List<Integer>> threeSumWithoutSorting(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        
        for (int i = 0; i < nums.length; i++) {
        	Map<Integer, int[]> map = new HashMap<Integer, int[]>();

        	for (int j = i + 1; j < nums.length; j++) {
        		if (map.containsKey(nums[j])) {
        			List<Integer> list = new ArrayList<Integer>();
        			int[] arr = map.get(nums[j]);
        			list.add(arr[0]);
        			list.add(arr[1]);
        			list.add(nums[j]);
        			
        			res.add(list);
        		}
        		
        		map.put(-nums[i] - nums[j], new int[]{nums[i], nums[j]});
        	}
        }
        
        return res;
    }
    
    public static void main(String[] args) {
    	ThreeSum sum3 = new ThreeSum();
    	int[] nums= {-1, 0, 1, -1, 2, -4};
    	List<List<Integer>> res = sum3.threeSumWithoutSorting(nums);
    	for (List<Integer> list : res) {
    		for (int val : list) {
    			System.out.print(val + "  ");
    		}
    		System.out.println();
    	}
	}
}