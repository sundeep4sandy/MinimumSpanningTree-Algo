// Java program for Kruskal's algorithm to find Minimum Spanning Tree
// of a given connected, undirected and weighted graph
package network;
import java.util.*;
import java.lang.*;
import java.io.*;

class Graph
{
	// A class to represent a graph edge
	class Edge implements Comparable<Edge>
	{
		int src, dest, weight,max_weight,node;

		// Comparator function used for sorting edges based on
		// their weight
		public int compareTo(Edge compareEdge)
		{
			return this.weight-compareEdge.weight;
		}
	};
	// new class for nodes in mst
	class Nodes
	{
		int des,weigh,nod;
	};

	// A class to represent a subset for union-find
	class subset
	{
		int node,parent, rank;
	};

	int V, E; // V-> no. of vertices & E->no.of edges
	Edge edge[]; // collection of all edges

	// Creates a graph with V vertices and E edges
	Graph(int v, int e)
	{
		V = v;
		E = e;
		edge = new Edge[E];
		for (int i=0; i<e; ++i)
			edge[i] = new Edge();
	}

	// A utility function to find set of an element i
	// (uses path compression technique)
	int find(subset subsets[], int i)
	{
		// find root and make root as parent of i (path compression)
		if (subsets[i].parent != i)
			subsets[i].parent = find(subsets, subsets[i].parent);

		return subsets[i].parent;
	}

	// A function that does union of two sets of x and y
	// (uses union by rank)
	void Union(subset subsets[], int x, int y)
	{
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);

		// Attach smaller rank tree under root of high rank tree
		// (Union by Rank)
		if (subsets[xroot].rank < subsets[yroot].rank)
			subsets[xroot].parent = yroot;
		else if (subsets[xroot].rank > subsets[yroot].rank)
			subsets[yroot].parent = xroot;

		// If ranks are same, then make one as root and increment
		// its rank by one
		else
		{
			subsets[yroot].parent = xroot;
			subsets[xroot].rank++;
		}
	}
	boolean flag=true;

	// The main function to construct MST using Kruskal's algorithm
	void KruskalMST()
	{
		Edge result[] = new Edge[V-1]; // Tnis will store the resultant MST

		Edge discard[] = new Edge[E];
		
		int e = 0; // An index variable, used for result[]
		int i = 0; // An index variable, used for sorted edges
		for (i=0; i<V-1; ++i)
			result[i] = new Edge();
		
		for (i=0; i<E; ++i)
			discard[i] = new Edge();
		//System.out.println("Length"+discard.length);

		// Step 1: Sort all the edges in non-decreasing order of their
		// weight. If we are not allowed to change the given graph, we
		// can create a copy of array of edges
		Arrays.sort(edge);

		// Allocate memory for creating V ssubsets
		subset subsets[] = new subset[V];
		for(i=0; i<V; ++i)
			subsets[i]=new subset();

		// Create V subsets with single elements
		for (int v = 0; v < V; ++v)
		{
			subsets[v].node = v;
			subsets[v].parent = v;
			subsets[v].rank = 0;
		}
		Nodes node[] = new Nodes[V];
		for(i=0; i<V; ++i)
			node[i]=new Nodes();
		for (int v = 0; v < V; ++v)
		{
			node[v].des = 0;
			node[v].weigh = 0;
			node[v].nod=v;
			edge[v].node=v;
			//System.out.println("nodes"+node[v].nod);
			
		}
		i = 0; // Index used to pick next edge
		int var = 0;
		int var2 = 0;

		// Number of edges to be taken is equal to V-1
		while (e < V - 1)
		{
			// Step 2: Pick the smallest edge. And increment the index
			// for next iteration
			Edge next_edge = new Edge();
			next_edge = edge[i++];

			int x = find(subsets, next_edge.src);
			int y = find(subsets, next_edge.dest);
			
			//System.out.println("X:"+ x);
			//System.out.println("Y:"+ y);

			// If including this edge does't cause cycle, include it
			// in result and increment the index of result for next edge
			if (x != y)
			{
				result[e++] = next_edge;
				Union(subsets, x, y);
			}
			// Else discard the next_edge
			else
			{
				//for (int j = 0; j<(E-V+1); j++){
					//if(discard[j] != next_edge){
				//System.out.println("e:"+e);
						discard[e-1] = next_edge;
						//}
					//	System.out.println(discard[e-1].src+" -- "+discard[e-1].dest+" == "+
						//	discard[e-1].weight);
				//}
			}
			    
			var = i;
			var2 = e;
			
		}
		//System.out.println(var);
		//System.out.println(var2);
		var2 = var2 -1;
		//System.out.println(var2);
		for(int j = var; j < E; j++)
		{
			Edge next_edge = new Edge();
			next_edge = edge[j];
			//System.out.println(edge[j].src+" -- "+edge[j].dest+" == "+
					//edge[j].weight);
			//while(var2<E-1 ){
			discard[var2] = next_edge;
		//	System.out.println(discard[var2].src+" -- "+discard[var2].dest+" == "+
				//	discard[var2].weight);
			var2++;
			//System.out.println(var2);
			//System.out.println(E-1);
			//}
			
			
		}

		// print the contents of result[] to display the built MST
		System.out.println("Following are the edges in the constructed MST");
		for (i = 0; i<result.length;i++)
			System.out.println(result[i].src+" -- "+result[i].dest+" == "+
							result[i].weight);
		for (i = 0; i <result.length;i++)
		{
			for(int j=0;j<node.length;j++)
			{
			if(result[i].src==node[j].nod)
			{
				//System.out.println("nodes in result"+result[i].src);
				node[j].des=result[i].dest;
				node[j].weigh=result[i].weight;
				
			}
			if(result[i].dest==node[j].nod)
			{
				node[j].des=result[i].src;
				node[j].weigh=result[i].weight;
				
			}
			}
			
			
		}
		//node[1].des=100;
		for (i = 0; i <node.length;i++)
		System.out.println("node"+node[i].nod+"dest"+node[i].des+"weight"+node[i].weigh);
			
		System.out.println("Following are the edges Discarded");
		Edge discard_final[] = new Edge[E];
		for (i=0; i<E; ++i)
			discard_final[i] = new Edge();
		int count = 0;
		for (int j = 0,p=0; j < discard.length;j++){
			Edge curr_edge = new Edge();
			curr_edge = discard[j];
			if(curr_edge.weight!=0){
				discard_final[p]=curr_edge;	
			System.out.println(discard_final[p].src+" -- "+discard_final[p].dest+" == "+
					discard_final[p].weight);
			p++;
			
			int x = find(subsets, curr_edge.src);
			int y = find(subsets, curr_edge.dest);
			//for (int k = 0; k < discard_final.length;k++)
				
			//System.out.println(discard_final[k].weight);
				
				
			
			//System.out.println("X:"+ x);
			//System.out.println("Y:"+ y);
			}
			//System.out.println(discard_final.length);
			//for (int k = 0; k < discard_final.length;k++)
			
			//System.out.println(discard_final[k].weight);
		}
		
		for(int v = 0; v<V; v++){
			System.out.println("Parent:"+subsets[v].parent+"Rank:"+subsets[v].rank+"Node:"+subsets[v].node);
			}
		int weight_final[]=new int[5];
	
	for(int t=0;t<3;t++)
	{
		flag=true;
		weight_final[0]=node[discard_final[t].src].weigh;
		int dest=node[discard_final[t].src].des;
		System.out.println(dest);
		for(int k=0,l=1;k<5;k++,l++){
			if(dest==discard_final[t].src){
				flag=false;
				System.out.println("flag false");
				for(int p=0;p<5;p++)
					weight_final[p]=0;
				break;
				
			}
		if(dest!=discard_final[t].dest){
			weight_final[l]=node[dest].weigh;
			System.out.println("weights"+weight_final[k]);
			dest=node[dest].des;
			}
		
			else break;
		}
	
	//for(int p=0;p<5;p++)
	//	System.out.println("weights"+weight_final[p]);
	if(flag==false)
	{
		for(i=0;i<1;i++)
		{
		
		int dest1=node[discard_final[t].dest].des;
		weight_final[0]=node[discard_final[t].dest].weigh;
		for(int k=0,l=1;k<5;k++,l++){
			if(dest1!=discard_final[t].src){
				weight_final[l]=node[dest1].weigh;
				//System.out.println(dest1);
				//System.out.println("weights"+weight_final[k]);
				dest1=node[dest1].des;
				
				
			}
			
				else break;
			}
	}
		
	}
	
	 Arrays.sort(weight_final);
	// for(int p=0;p<5;p++)
			//System.out.println("weights"+weight_final[p]);
	 int max = weight_final[weight_final.length - 1];
	 System.out.println("max"+max);
	 for(int r=0;r<5;r++)
	 {
		 for(int j=0;j<5;j++)
		 {
			// System.out.println(edge[j].weight);
		 if(edge[j].weight==weight_final[r])
			 
		 {
			 if(edge[j].max_weight!=0)
			edge[j].max_weight=Math.min(max,edge[j].max_weight);
			 else
				 edge[j].max_weight=max;
			System.out.println(edge[j].src+"--"+edge[j].dest+"="+"{0,"+edge[j].max_weight+"}");
		 }
		 }
	 }
	 
	}
	
		
			
	}
		
		
		
		
		
			
	

	

	



	// Driver Program
	public static void main (String[] args)
	{

		/* Let us create following weighted graph
				10
			0--------1
			| \	 |
		6| 5\ |15
			|	 \ |
			2--------3
				4	 */
		int V = 5; // Number of vertices in graph
		int E = 10; // Number of edges in graph
		Graph graph = new Graph(V, E);

		// add edge 0-1
		graph.edge[0].src = 0;
		graph.edge[0].dest = 1;
		graph.edge[0].weight = 7;

		// add edge 0-2
		graph.edge[1].src = 0;
		graph.edge[1].dest = 2;
		graph.edge[1].weight = 6;

		// add edge 0-3
		graph.edge[2].src = 0;
		graph.edge[2].dest = 3;
		graph.edge[2].weight = 5;

		// add edge 1-3
		graph.edge[3].src = 1;
		graph.edge[3].dest = 3;
		graph.edge[3].weight = 15;

		// add edge 2-3
		graph.edge[4].src = 2;
		graph.edge[4].dest = 3;
		graph.edge[4].weight = 4;
		
		graph.edge[5].src = 2;
		graph.edge[5].dest = 1;
		graph.edge[5].weight = 8;
		
		graph.edge[6].src = 1;
		graph.edge[6].dest = 4;
		graph.edge[6].weight = 15;
		
		graph.edge[7].src = 3;
		graph.edge[7].dest = 4;
		graph.edge[7].weight = 18;
		
		graph.edge[8].src = 0;
		graph.edge[8].dest = 4;
		graph.edge[8].weight = 8;
		
		graph.edge[9].src = 2;
		graph.edge[9].dest = 4;
		graph.edge[9].weight = 3;
		
		

		graph.KruskalMST();
	}
}

