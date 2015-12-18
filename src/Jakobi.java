/**
 * Created by Кирилл on 27.10.2015.
 */
public class Jakobi {
    private int m;
    private int n;
    private double x[][];
    private Matrix A;
    private Matrix b;
    private double epsilon;
    private int number = 0;

    public Jakobi(Matrix A, Matrix b,double epsilon){
        this.A=A;
        this.b=b;
        if(A.getRowDimension()!=b.getRowDimension()) throw  new IllegalArgumentException("Wrong number of rows");
        n = A.getColumnDimension();
        m = A.getRowDimension();
        this.epsilon = epsilon;
    }

    public Matrix solve(){
        double[][] A1 = A.getArrayCopy();

        if(!CanSolve()) throw new RuntimeException("System cannot be resolve");
        double [][]xold  = b.getArrayCopy();
        double [][] xnew = new double[b.getRowDimension()][b.getColumnDimension()];

        double[] xo = new double[m];
        double[] xn = new double[m];
//        double[][] b1 = b.getArrayCopy();
        for (int j = 0; j < b.getColumnDimension(); j++) {

            do {
                for (int i = 0; i < m; i++) {
                    if(xnew[i][j]!=0) {
                        xold[i][j] = xnew[i][j];
                        xnew[i][j] = 0;
                    }
                }

                for (int i = 0; i < m; i++) {
                    for (int k = 0; k < i ; k++) {
                        xnew[i][j] -= A1[i][k] / A1[i][i] * xold[k][j];
                        number+=3;
                    }

                    for (int k = i+1; k < m; k++) {
                        xnew[i][j] -= A1[i][k] / A1[i][i] * xold[k][j];
                        number+=3;
                    }

                    xnew[i][j] += b.get(i, j) / A1[i][i];
                    number+=2;

                }

                for (int i = 0; i < m; i++) {
                    xo[i] = xold[i][j];
                    xn[i]= xnew[i][j];
                }


            }while (!EndOf(new Matrix(xo,m),new Matrix(xn,m)));
        }
        this.x = xnew;
        return new Matrix(xnew,b.getRowDimension(),b.getColumnDimension());
    }



    public boolean EndOf(Matrix x, Matrix y){
        if(x.getColumnDimension()!=1 || y.getColumnDimension()!=1) throw  new IllegalArgumentException("Must be a vector");
        if(x.getRowDimension()!=y.getRowDimension()) throw  new IllegalArgumentException("Number of rows must be the same");
        Matrix z = x.minus(y);
        if(z.norm1vector()<(1-q())/q()*epsilon){
        return true;
        }
        else return false;
    }

    double [] Error(Matrix Xet, int norm){
        Matrix Xerr = new Matrix(this.x);
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


    public double q(){
        if(A.getColumnDimension()!= A.getRowDimension()) throw new IllegalArgumentException("Matrix must be square");
        double[][] RplusL = A.getArrayCopy();
        double[][] D = new double[m][m];
        for(int i=0; i<m;i++){
            D[i][i] = -1.0/RplusL[i][i];
            RplusL[i][i] = 0.0;
        }
        return new Matrix(D).times(new Matrix(RplusL)).norm1();

    }


    public boolean CanSolve(){
        if(A.getRowDimension()!=A.getColumnDimension()) throw new IllegalArgumentException("Matrix must be sqare");
        double[][] DminusA = A.getArrayCopy();
        double[][] Dmin = A.getArrayCopy();
        for (int i = 0; i<m; i++){
            for (int k = 0; k<m; k++){
                if(i==k){
                    DminusA[i][k] = 0;
                    Dmin[i][k] = -1.0/Dmin[i][k];
                }else{
                    Dmin[i][k] = 0;
                    DminusA[i][k] = -DminusA[i][k];
                }
            }
        }
        Matrix result = new Matrix(Dmin).times(new Matrix(DminusA));
        return (result.norm1()<1);
    }

    public int getNumber(){
        return number;
    }
}

