/** LU Decomposition.
   <P>
   For an m-by-n matrix A with m >= n, the LU decomposition is an m-by-n
   unit lower triangular matrix L, an n-by-n upper triangular matrix U,
   and a permutation vector piv of length m so that A(piv,:) = L*U.
   If m < n, then L is m-by-m and U is m-by-n.
   <P>
   The LU decompostion with pivoting always exists, even if the matrix is
   singular, so the constructor will never fail.  The primary use of the
   LU decomposition is in the solution of square systems of simultaneous
   linear equations.  This will fail if isNonsingular() returns false.
   */

public class LUDecomposition implements java.io.Serializable {

/* ------------------------
   Class variables
 * ------------------------ */

   /** Array for internal storage of decomposition.
   @serial internal array storage.
   */
   private double[][] LU, B, X;

   /** Row and column dimensions, and pivot sign.
   @serial column dimension.
   @serial row dimension.
   @serial pivot sign.
   */
   private int m, n, pivsign=1;

   /** Internal storage of pivot vector.
   @serial pivot vector.
   */
   private int[] piv;

   /** Number of arithmetic operations
    @serial number.
    */
   private int number = 0;

/* ------------------------
   Constructor
 * ------------------------ */

   /** LU Decomposition
       Structure to access L, U and piv.
   @param  A Rectangular matrix
   */

   public LUDecomposition (Matrix A, int y) {

   // Use a "left-looking", dot-product, Crout/Doolittle algorithm.

      if(y>1 || y<0){
         throw new IllegalArgumentException("Wrong type of decomposition");
      }

      B = A.getArrayCopy();
      m = A.getRowDimension();
      n = A.getColumnDimension();
      LU = new double[m][n];
      piv = new int[n];
      for (int i = 0; i < n; i++) {
         piv[i] = i;
      }


      for(int i=0;i<m;i++){
         for(int k=i;k<n;k++){
            LU[i][k] = B[i][k];
            for(int b=0;b<i;b++){
               LU[i][k]=LU[i][k]-LU[i][b]*LU[b][k];
               number+=2;
            }
         }
         if(y == 1) {
            double maxRowEl = Math.abs(LU[i][i]);
            int maxRowElInd = i;
            for (int f = i; f < n; f++) {
               if (maxRowEl < Math.abs(LU[i][f])) {
                  maxRowEl = Math.abs(LU[i][f]);
                  maxRowElInd = f;
               }
            }
            if (maxRowElInd != i) {

//               piv[maxRowElInd] += piv[i];
//               piv[i] = piv[maxRowElInd] - piv[i];
//               piv[maxRowElInd] -= piv[i];
                  int t = piv[maxRowElInd];
                  piv[maxRowElInd] = piv[i];
                  piv[i] = t;

               for (int g = 0; g < m; g++) {

                     double e = LU[g][i];
                     LU[g][i] = LU[g][maxRowElInd];
                     LU[g][maxRowElInd] = e;

                     e = B[g][i];
                     B[g][i] = B[g][maxRowElInd];
                     B[g][maxRowElInd] = e;


//                  LU[g][i] += LU[g][maxRowElInd];
//                  LU[g][maxRowElInd] = LU[g][i] - LU[g][maxRowElInd];
//                  LU[g][i] -= LU[g][maxRowElInd];
//                  B[g][i] += B[g][maxRowElInd];
//                  B[g][maxRowElInd] = B[g][i] - B[g][maxRowElInd];
//                  B[g][i] -= B[g][maxRowElInd];

                  pivsign = -pivsign;
               }
            }
         }
         for(int l=i+1;l<m;l++){
            LU[l][i]=B[l][i];
            for(int h=0;h<i;h++){
               LU[l][i]=LU[l][i]-LU[l][h]*LU[h][i];
               number+=2;
            }
            LU[l][i]=LU[l][i]/LU[i][i];
            number++;
         }
      }

   }


/* ------------------------
   Public Methods
 * ------------------------ */

   /** Is the matrix nonsingular?
   @return     true if U, and hence A, is nonsingular.
   */

   public boolean isNonsingular () {
      for (int j = 0; j < n; j++) {
         if (LU[j][j] == 0)
            return false;
      }
      return true;
   }

   /** Return lower triangular factor
   @return     L
   */

   public Matrix getL () {
      Matrix X = new Matrix(m,n);
      double[][] L = X.getArray();
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++) {
            if (i > j) {
               L[i][j] = LU[i][j];
            } else if (i == j) {
               L[i][j] = 1.0;
            } else {
               L[i][j] = 0.0;
            }
         }
      }
      return X;
   }

   /** Return upper triangular factor
   @return     U
   */

   public Matrix getU () {
      Matrix X = new Matrix(n,n);
      double[][] U = X.getArray();
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            if (i <= j) {
               U[i][j] = LU[i][j];
            } else {
               U[i][j] = 0.0;
            }
         }
      }
      return X;
   }

   /** Return pivot permutation vector
   @return     piv
   */

   public int[] getPivot () {
      int[] p = new int[m];
      for (int i = 0; i < m; i++) {
         p[i] = piv[i];
      }
      return p;
   }

   /** Return pivot permutation vector as a one-dimensional double array
   @return     (double) piv
   */

   public double[] getDoublePivot () {
      double[] vals = new double[m];
      for (int i = 0; i < m; i++) {
         vals[i] = (double) piv[i];
      }
      return vals;
   }

   /** Determinant
   @return     det(A)
   @exception  IllegalArgumentException  Matrix must be square
   */

   public double det () {
      if (m != n) {
         throw new IllegalArgumentException("Matrix must be square.");
      }
      double d = (double) pivsign;
      for (int j = 0; j < n; j++) {
         d *= LU[j][j];
      }
      return d;
   }

   /** Solve A*X = B
   @param  B   A Matrix with as many rows as A and any number of columns.
   @return     X so that L*U*X = B(piv,:)
   @exception  IllegalArgumentException Matrix row dimensions must agree.
   @exception  RuntimeException  Matrix is singular.
   */

   public Matrix solve (Matrix B) {
      if (B.getRowDimension() != m) {
         throw new IllegalArgumentException("Matrix row dimensions must agree.");
      }
      if (!this.isNonsingular()) {
         throw new RuntimeException("Matrix is singular.");
      }

      int nx = B.getColumnDimension();
      Matrix Xmat = B.copy();
      double[][] X = Xmat.getArray();

      // Solve L*Y = B
      for (int k = 0; k < n; k++) {
         for (int i = k+1; i < n; i++) {
            for (int j = 0; j < nx; j++) {
               X[i][j] -= X[k][j]*LU[i][k];
               number+=2;
            }
         }
      }

      // Solve U*X = Y;
      for (int k = n-1; k >= 0; k--) {
         for (int j = 0; j < nx; j++) {
            X[k][j] /= LU[k][k];
            number++;
         }
         for (int i = 0; i < k; i++) {
            for (int j = 0; j < nx; j++) {
               X[i][j] -= X[k][j]*LU[i][k];
               number+=2;
            }
         }
      }
//      Xmat = Xmat.getMatrix(piv,0,0);
//      Xmat.setMatrix(piv,0,0,Xmat);
//      this.X = Xmat.getArrayCopy();
      this.X = Xmat.getArray();
      double [][] Xold = Xmat.getArrayCopy();
      for(int i = 0; i < m ; i++){
         for (int k = 0; k< Xmat.getColumnDimension();k++){
            X[piv[i]][k] = Xold[i][k];
         }
      }
      return new Matrix(X);

   }

   public Matrix Inverse(){
      if (m != n) {
         throw new IllegalArgumentException("Matrix must be square.");
      }
      Matrix A = new Matrix(m,m);
      double[][] X = A.getArray();
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < m; j++) {
            X[i][j] = (i == j ? 1.0 : 0.0);
         }
      }
      return this.solve(A);
   }

   double cond(int i){
      if(i>3 || i<1) throw new IllegalArgumentException("No such norm");
      Matrix A1 = new Matrix(B);
      Matrix A2 = this.Inverse();
      double MatrixCond = 0;
      switch (i){
         case 1:
         {MatrixCond = A1.norm1()*A2.norm1();}
         break;
         case 2:
         {MatrixCond = A1.norm2()*A2.norm2();}
         break;
         case 3:
         {MatrixCond =  A1.normInf()*A2.normInf();}
         break;
      }
      return MatrixCond;
   }

   double [] Error(Matrix Xet, int norm){
      Matrix Xerr = new Matrix(this.X);
      if(Xerr.getColumnDimension()!=Xet.getColumnDimension() || Xerr.getRowDimension()!=Xet.getRowDimension()) throw new IllegalArgumentException("Wrong matrix size");
      if(norm<1||norm>3) throw new IllegalArgumentException("Wrong type of vector norm");
      double[] err = new double[Xerr.getColumnDimension()];
      Matrix deltaXerr = Xerr.minus(Xet);
      int [] rowind = new int[m];
      for (int i = 0; i < m; i++) {
         rowind[i]=i;
      }
      for (int i = 0; i < Xerr.getColumnDimension(); i++) {
         switch (norm) {
            case 1: {
               err[i] = deltaXerr.getMatrix(rowind, i, i).norm1vector() / Xet.getMatrix(rowind, i, i).norm1vector();
            }break;
            case 2: {
               err[i] = deltaXerr.getMatrix(rowind, i, i).norm2vector() / Xet.getMatrix(rowind, i, i).norm2vector();
            }break;
            case 3: {
               err[i] = deltaXerr.getMatrix(rowind, i, i).normInfvector() / Xet.getMatrix(rowind, i, i).normInfvector();
            }break;
         }
      }
      return err;
   }


   public int getNumber(){
      return number;
   }


  private static final long serialVersionUID = 1;
}
