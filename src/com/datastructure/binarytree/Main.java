package com.datastructure.binarytree;

import java.util.Arrays;

/**
 * @Author zhshspark
 * @Date 2020-10-14 19:26:38
 *
 */
public class Main {

	public static void main(String[] args) {
		//构建二维数组
		int[] arr= {1,2,3,4,5,6,7,8,9,10,11,12,13};
		//创建二叉树并获取其根结点
		BinaryTree bt=new BinaryTree(arr,13);
		BinaryTreeNode root=bt.getRoot();
		
		//打印二维数组
		System.out.println("二维数组:"+Arrays.toString(arr));
		
		System.out.println();
		System.out.print("递归先序遍历：  ");
		bt.preOrderWithRecursion(root);
		
		System.out.println();
		System.out.print("非递归先序遍历：");
		bt.preOrderNotWithRecursion(root);
		
		System.out.println();
		System.out.print("递归中序遍历：  ");
		bt.inOrderWithRecursion(root);
		
		System.out.println();
		System.out.print("非递归中序遍历：");
		bt.inOrderNotWithRecursion(root);
		
		System.out.println();
		System.out.print("递归后序遍历：  ");
		bt.postOrderWithRecursion(root);
		
		System.out.println();
		System.out.print("非递归后序遍历：");
		bt.postOrderNotWithRecursion(root);
		
		System.out.println();
		System.out.print("使用队列进行广度优先搜索，层序遍历：");
		bt.bfsWithQueue(root);
		
		System.out.println();
		System.out.print("使用递归进行深度优先搜索遍历：");
		bt.dfsWithRecursion(bt.getRoot());
		
		System.out.println();
		System.out.print("使用栈进行深度优先搜索遍历：");
		bt.dfsWithStack(bt.getRoot());
		
		System.out.println();
		System.out.println("树的深度为："+String.valueOf(bt.getDepth()));
		
		
		//通过先序和中序遍历数组建立
		int[] pre= {1 , 2 , 4 , 8,  9 , 5 , 10 , 11 , 3 , 6 , 12,  13,  7 };
		int[] mid= {8 , 4 , 9 , 2, 10 , 5 , 11 , 1  ,12 , 6 , 13,  3  ,7};
		BinaryTree btt=new BinaryTree(pre,mid,null,true);
		System.out.println();
		System.out.print("使用队列进行广度优先搜索，层序遍历：");
		btt.bfsWithQueue(btt.getRoot());
		
		//通过中序和后序遍历数组建立
		int[] post= {8 , 9 , 4 , 10 , 11 , 5 , 2 , 12 , 13 , 6 , 7 , 3 , 1 };
		BinaryTree bttt=new BinaryTree(null,mid,post,false);
		System.out.println();
		System.out.print("使用队列进行广度优先搜索，层序遍历：");
		bttt.bfsWithQueue(bttt.getRoot());
	}
	
	
	
}
