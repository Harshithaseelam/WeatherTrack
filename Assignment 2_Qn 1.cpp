#include <bits/stdc++.h>
using namespace std;

                            // variable names and their meaning
                            // r : row
                            // c : column
                            // lr : left row
                            // ld : lower diagonal
                            // ud : upper diagonal


    void solve(int c, vector<string>&Board, vector<vector<string>>&result, vector<int>&lr, vector<int>&ud, vector<int>&ld, int n) {
      if (c == n) {
        result.push_back(Board);
        return;
      }
      for (int r = 0; r < n; r++) {
        if (lr[r] == 0 && ld[r + c] == 0 && ud[n - 1 + c - r] == 0) {
          Board[r][c] = 'Q';
          lr[r] = 1;
          ld[r + c] = 1;
          ud[n - 1 + c - r] = 1;
          solve(c + 1, Board, result, lr, ud, ld, n);
          Board[r][c] = '.';
          lr[r] = 0;
          ld[r + c] = 0;
          ud[n - 1 + c - r] = 0;
        }
      }
    }


    vector<vector<string>>solve_N_Queens(int n) {
      
      vector<string>Board(n);
      
      string s(n, '.');
      for (int i = 0; i < n; i++) {
        Board[i] = s;
      }
      
      vector<vector<string>>result;
      

     
      vector<int>lr(n, 0),ud(2 * n - 1, 0),ld(2 * n - 1, 0);
      
      solve(0, Board,result, lr, ud, ld, n);
      return result;
      
    }
    
int main() {
  int n;
  cin>>n;
  vector<vector<string>>res = solve_N_Queens(n);
  for (int i = 0; i < res.size(); i++) {
   
    for (int j = 0; j < res[0].size(); j++) {
      cout << res[i][j];
      cout << endl;
    }
    cout << endl;
  }
  return 0;
}
