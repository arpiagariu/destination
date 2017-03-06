package leetcode16.segmenttree.binaryindextree;
/**
 * The structure of Segment Tree is a binary tree which each node has two attributes start and end denote an segment / interval.

start and end are both integers, they should be assigned in following rules:

The root's start and end is given by build method.
The left child of node A has start=A.left, end=(A.left + A.right) / 2.
The right child of node A has start=(A.left + A.right) / 2 + 1, end=A.right.
if start equals to end, there will be no children for this node.
Implement a build method with a given array, so that we can create a corresponding segment tree with every node value represent the corresponding interval max value in the array, return the root of this segment tree.

Have you met this question in a real interview? Yes
Clarification
Segment Tree (a.k.a Interval Tree) is an advanced data structure which can support queries like:

which of these intervals contain a given point
which of these points are in a given interval
See wiki:
Segment Tree
Interval Tree

Example
Given [3,2,1,4]. The segment tree will be:

                 [0,  3] (max = 4)
                  /            \
        [0,  1] (max = 3)     [2, 3]  (max = 4)
        /        \               /             \
[0, 0](max = 3)  [1, 1](max = 2)[2, 2](max = 1) [3, 3] (max = 4)
 */
public class SegmentTreeBuild2 {
    public SegmentTreeNode build(int[] A) {
        // write your code here
        return helper(A, 0, A.length - 1);
    }
    
    SegmentTreeNode helper(int[] A, int left, int right) {
        if (left > right) {
            return null;
        }
        
        SegmentTreeNode root = new SegmentTreeNode(left, right, A[left]);
        if (left == right) {
            return root;
        }
        
        int mid = left + (right - left) / 2;
        root.left = helper(A, left, mid);
        root.right = helper(A, mid + 1, right);
        
        root.max = Math.max(root.left.max, root.right.max);
        
        return root;
    }
}
