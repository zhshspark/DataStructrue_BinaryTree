package com.datastructure.binarytree;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntPredicate;

import com.sun.source.tree.Tree;

import java.util.Queue;
import java.util.Stack;

/**
 * @Author zhshspark
 * @Date 2020-10-14 20:22:15
 *
 */
public class BinaryTree {
	private BinaryTreeNode root=null;
	
	//构造函数,通过一维数组创建
	public BinaryTree(int[] arr,int len) {
		root=createBinaryTreeByArray(arr,len,0);
	}

	//重构树，根据先序和中序遍历数组,type==ture,传入先序和中序数组；type=false，传入中序和后序数组
	public BinaryTree(int[] pre,int[]mid,int[]post,boolean type) {
		root=(type==true)?buildBinaryTreeByPreAndMidArray(pre,mid):buildBinaryTreeByMidAndPostArray(mid,post);
	}
	
	//获取树的根结点
	public BinaryTreeNode getRoot() {
		return root;
	}
	
	//获取树的深度
	public int getDepth() {
		Integer treeDepth=0;
		//层序遍历
		Queue<Entry<BinaryTreeNode,Integer>> qu=new LinkedList<Entry<BinaryTreeNode,Integer>>();
		if (root!=null) {
			qu.add(new AbstractMap.SimpleEntry(root,treeDepth));
		}
		while (!qu.isEmpty()) {
			BinaryTreeNode tmp=qu.poll().getKey();
			if (tmp.left!=null || tmp.right!=null) {
				treeDepth++;
			}
			
			if (tmp.left!=null) {
				qu.add(new AbstractMap.SimpleEntry(tmp.left,treeDepth));
			}
			if (tmp.right!=null) {
				qu.add(new AbstractMap.SimpleEntry(tmp.right,treeDepth));
			}
		}
		return treeDepth;
	}
	
	//通过一维数组创建二叉树
	public BinaryTreeNode createBinaryTreeByArray(int a[],int len,int index) {
		if (index>=len) {
			return null;
		}
		
		BinaryTreeNode node=new BinaryTreeNode(a[index]);
		
		node.left =createBinaryTreeByArray( a, len, 2*index+1);
		node.right=createBinaryTreeByArray( a, len, 2*index+2);
		
		return node;
	}	
	
	//通过前序遍历和中序遍历数组还原二叉树
	public BinaryTreeNode buildBinaryTreeByPreAndMidArray(int[]pre,int[]mid) {
		if (pre==null||mid==null||pre.length<1||mid.length<1) {
			return null;
		}
		//首先确定根结点，前序遍历数组中的第一个值就是根结点
		BinaryTreeNode treeRoot=new BinaryTreeNode(pre[0]);
		//获取根结点在中序遍历数组中的索引
		int rootIndex=getIndex(mid,treeRoot.val);
		if (rootIndex==-1) {
			System.out.println("输入的前序遍历数组或中序遍历数组不正确，请确认！");
			return null;
		}
		//找到前序和中序中的左子树,copyOfRange左闭右开
		int[] leftMid=Arrays.copyOfRange(mid,0,rootIndex);
		int[] leftPre=Arrays.copyOfRange(pre,1,rootIndex+1);
		
		//找到前序和中序中的右子树
		int[] rightMid=Arrays.copyOfRange(mid,rootIndex+1,mid.length);
		int[] rightPre=Arrays.copyOfRange(pre,rootIndex+1,pre.length);
		
		//用递归再次构建左右子树
		BinaryTreeNode left=buildBinaryTreeByPreAndMidArray(leftPre,leftMid);
		BinaryTreeNode right=buildBinaryTreeByPreAndMidArray(rightPre,rightMid);
		
		//将左右子树加到根结点上
		treeRoot.left=left;
		treeRoot.right=right;
		return treeRoot;
	}
	
	//通过后序遍历和中序遍历数组还原二叉树
	public BinaryTreeNode buildBinaryTreeByMidAndPostArray(int[]mid,int[]post) {
		int midLen=mid.length;
		int postLen=post.length;
		if (post==null||mid==null||midLen<1||postLen<1) {
			return null;
		}
		//首先确定根结点，后序遍历数组中的最后一个值就是根结点
		BinaryTreeNode treeRoot=new BinaryTreeNode(post[postLen-1]);
		//获取根结点在中序遍历数组中的索引
		int rootIndex=getIndex(mid,treeRoot.val);
		if (rootIndex==-1) {
			System.out.println("输入的中序遍历数组或后序遍历数组不正确，请确认！");
			return null;
		}
		//找到中序和后序中的左子树,copyOfRange左闭右开
		int[] leftMid=Arrays.copyOfRange(mid,0,rootIndex);
		int[] leftPost=Arrays.copyOfRange(post,0,rootIndex);
		
		//找到中序和后序中的右子树
		int[] rightMid=Arrays.copyOfRange(mid,rootIndex+1,midLen);
		int[] rightPost=Arrays.copyOfRange(post,rootIndex,postLen-1);
		
		//用递归再次构建左右子树
		BinaryTreeNode left=buildBinaryTreeByMidAndPostArray(leftMid,leftPost);
		BinaryTreeNode right=buildBinaryTreeByMidAndPostArray(rightMid,rightPost);
		
		//将左右子树加到根结点上
		treeRoot.left=left;
		treeRoot.right=right;
		return treeRoot;
	}
	
	//获取一个值在数组中的索引
	private int getIndex(int[] mid, int val) {
		for (int i = 0; i < mid.length; i++) {
			if (mid[i]==val) {
				return i;
			}
		}
		return -1;
	}

	//递归先序遍历二叉树
	public void preOrderWithRecursion(BinaryTreeNode treeRoot)
	{
		if (treeRoot==null) {
			return;
		}
		//处理遍历到的结点，中间结点
		System.out.print(treeRoot.val+"  ");
		//递归遍历左子树
		preOrderWithRecursion(treeRoot.left);
		//递归遍历右子树
		preOrderWithRecursion(treeRoot.right);
	}
	
	//非递归先序遍历二叉树
	public void preOrderNotWithRecursion(BinaryTreeNode treeRoot) {
		if (treeRoot==null) {
			return;
		}
		Stack<BinaryTreeNode> stack=new Stack<BinaryTreeNode>();
		BinaryTreeNode curr=treeRoot;
		while (curr!=null||!stack.isEmpty()) {
			while (curr!=null) {
				//处理遍历到的结点
				System.out.print(curr.val+"  ");
				stack.push(curr);
				curr=curr.left;
			}
			if (!stack.isEmpty()) {
				curr=stack.pop();
				curr=curr.right;
			}
		}
	}
	
	//递归中序遍历二叉树
	public void inOrderWithRecursion(BinaryTreeNode treeRoot) {
		if (treeRoot==null) {
			return;
		}

		//递归遍历左子树
		inOrderWithRecursion(treeRoot.left);
		//处理遍历到的结点，中间结点
		System.out.print(treeRoot.val+"  ");
		//递归遍历右子树
		inOrderWithRecursion(treeRoot.right);
	}
	
	//非递归中序遍历二叉树
	public void inOrderNotWithRecursion(BinaryTreeNode treeNode) {
		if (treeNode==null) {
			return;
		}
		Stack<BinaryTreeNode> stack=new Stack<BinaryTreeNode>();
		BinaryTreeNode node=treeNode;
		while (node!=null||!stack.isEmpty()) {
			while (node!=null) {
				stack.push(node);
				node=node.left;
			}
			if (!stack.isEmpty()) {
				node=stack.pop();
				System.out.print(node.val+"  ");
				node=node.right;
			}
		}
	}
	
	//递归后续遍历二叉树
	public void postOrderWithRecursion(BinaryTreeNode treeRoot) {
		if (treeRoot==null) {
			return;
		}

		//递归遍历左子树
		postOrderWithRecursion(treeRoot.left);
		//递归遍历右子树
		postOrderWithRecursion(treeRoot.right);
		//处理遍历到的结点，中间结点
		System.out.print(treeRoot.val+"  ");
	}
	
	//非递归后续遍历二叉树	
	public void postOrderNotWithRecursion(BinaryTreeNode treeRoot) {
		if (treeRoot==null) {
			return;
		}
		Stack<BinaryTreeNode> stack=new Stack<BinaryTreeNode>();
		BinaryTreeNode curr=treeRoot,prev=treeRoot;
		while (!stack.isEmpty()||curr!=null) {
			while (curr!=null) {
				stack.push(curr);
				curr=curr.left;
			}
			if (!stack.isEmpty()) {
				BinaryTreeNode tmp=stack.peek().right;
				if (tmp==null||tmp==prev) {
					curr=stack.pop();
					System.out.print(curr.val+"  ");
					prev=curr;
					curr=null;
				}
				else {					
					curr=tmp;
				}
			}
		}
	}
	
	//BFS, Breadth First Search
	//即一层层的遍历，所以要用队列
	public void bfsWithQueue(BinaryTreeNode root) {
		Queue<BinaryTreeNode> qu=new LinkedList<BinaryTreeNode>();
		if(root!=null) {
			qu.add(root);
		}
		while (!qu.isEmpty()) {
			BinaryTreeNode tmp=qu.poll();
			//处理遍历到的结点
			System.out.print(tmp.val+"  ");
			
			//加入tmp的左右子树
			if (tmp.left!=null) {
				qu.add(tmp.left);
			}
			if (tmp.right!=null) {
				qu.add(tmp.right);
			}
		}
	}

	//DFS, Deepth First Search
	//即优先深度进行遍历，所以要用递归和栈
	public void dfsWithRecursion(BinaryTreeNode root) {
		if (root==null) {
			return;
		}
		//处理遍历到的结点
		System.out.print(root.val+"  ");
		
		//递归调用左子树
		if (root.left!=null) {
			dfsWithRecursion(root.left);
		}
		//递归调用右子树
		if (root.right!=null) {
			dfsWithRecursion(root.right);
		}
	}
	
	//DFS, Deepth First Search
	//使用栈的DFS
	public void dfsWithStack(BinaryTreeNode root) {
		if (root==null) {
			return;
		}
		Stack<BinaryTreeNode> stack=new Stack<BinaryTreeNode>();
		stack.push(root);
		
		while (!stack.isEmpty()) {
			BinaryTreeNode tmp=stack.pop();
			//处理搜索到的结点
			System.out.print(tmp.val+"  ");
			
			//处理右子树
			if(tmp.right!=null) {
				stack.push(tmp.right);
			}			
			//处理左子树
			if (tmp.left!=null) {
				stack.push(tmp.left);
			}

		}
	}
	
    /**
     * @param T1: The roots of binary tree T1.
     * @param T2: The roots of binary tree T2.
     * @return: True if T2 is a subtree of T1, or false.
     * 有两个不同大小的二叉树： T1 有上百万的节点； T2 有好几百的节点。请设计一种算法，判定 T2 是否为 T1的子树
     * 
     */
	
	//第一种方法：检测是否是子树，递归遍历，其实很简单，但是自己就是做不出，虽然几个月前也做过一次~！
    public static boolean isSubtree(BinaryTreeNode T1, BinaryTreeNode T2) {
    	if(T1==null && T2==null) {
    		return true;
    	}
    	if (T1==null) {
			return false;
		}
    	if (isSame(T1,T2)) {
			return true;
		}
    	return isSubtree(T1.left, T2) ||isSubtree(T1.right,T2);
    }

	private static boolean isSame(BinaryTreeNode t1, BinaryTreeNode t2) {
		if (t1==null && t2==null) {
			return true;
		}
		if (t1==null||t2==null) {
			return false;
		}
		if (t1.val!=t2.val) {
			return false;
		}
		return isSame(t1.left, t2.left) && isSame(t1.right, t2.right);
	}
	
	//第二种方法，将两树都转换为字符串，然后寻找子串。
	//这种方法有个雷，虽然1,2是1,2,#，3的子串，但是并不是其子树，所以遍历的时候必须要加上分隔符
	public static  boolean isSubtreeSec(BinaryTreeNode t1, BinaryTreeNode t2) {
		if (t1==null &&t2==null) {
			return true;
		}
		if (t1==null) {
			return false;
		}
		StringBuilder sbT1=new StringBuilder();
		getString(t1,sbT1);
		StringBuilder sbT2=new StringBuilder();
		getString(t2,sbT2);
		if(sbT1.indexOf(sbT2.toString())>=0){
			return true;
		}
		return false;
	}

	private static void getString(BinaryTreeNode t1, StringBuilder sbT1) {
		if (t1==null) {
			sbT1.append(",#");
			return;
		}
		sbT1.append(","+t1.val);
		getString(t1.left, sbT1);
		getString(t1.right, sbT1);
	}
	
}
