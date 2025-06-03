#include <bits/stdc++.h>
using namespace std;

	bool dfs(int node, vector<vector<int>> &adj, vector<int>&vis1, vector<int>&vis2) {
		vis1[node] = 1;
	    vis2[node] = 1;
		for (auto it : adj[node]) {
		    
			// when the node is not visited
			if (!vis1[it]) {
				if (dfs(it, adj, vis1, vis2) == true)
					return true;
			}
			// if the node has been previously visited
			// but it has to be visited on the same path
			else if (vis2[it]) {
				return true;
			}
		}

		vis2[node] = 0;
		return false;
	}

    // function to check circular dependency
	bool hasCircularDependency(int n, vector<vector<int>>& edges) {
	   
	   
	   vector<int>vis1(n,0);
		vector<int>vis2(n,0);
	    
	    // adjacency matrix
	    vector<vector<int>>adj;
	    
	    // initialisation of adjacency matrix
	    for(int i=0;i<n;i++){
	        adj.push_back({});
	    }
	    
	    for(int i=0;i<edges.size();i++){
	        adj[edges[i][0]].push_back(edges[i][1]);
	    }
	    
	    

		for (int i = 0; i < n; i++) {
			if (!vis1[i]) {
				if (dfs(i, adj, vis1, vis2) == true) {
				    return true;
				}
				
			}
		}
		return false;
	}



    int main() {

        // number of vertices
        int n;
        cin>>n;

        // vector to store edges in a graph
        vector<vector<int>>edges;

        // number of edges in a graph
        int e;
        cin>>e;

        int a,b;

        for(int i=0;i<e;i++){
            cin>>a>>b;
        edges.push_back({a,b});
        }
        if(hasCircularDependency(n,edges)){
            cout<<"True"<<endl;
        }
        else{
            cout<<"False"<<endl;
        }
        


	   return 0;
    }
