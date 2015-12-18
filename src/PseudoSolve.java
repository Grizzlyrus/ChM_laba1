/**
 * Created by Кирилл on 18.12.2015.
 */
public class PseudoSolve {
    private Matrix A;
    private int Am;
    private int An;
    private int m;
    private Matrix PseudoMatrix;
    private int k;
    private Matrix solution;
    private double[][] X;
    private double cond2;

    public PseudoSolve(int k, Matrix A){
        this.A= A;
        this.k=k;
        this.Am=A.getRowDimension();
        this.An=A.getColumnDimension();
        PseudoMatrix = A.transpose().times(A);
        this.m=PseudoMatrix.getRowDimension();
        for (int i=0;i<PseudoMatrix.getColumnDimension();i++){
            PseudoMatrix.set(i,i,PseudoMatrix.get(i,i)+Math.pow(10,-k));
        }
        SingularValueDecomposition svd = new SingularValueDecomposition(PseudoMatrix);
        cond2 = (svd.getMaxSingularValue()+Math.pow(10,-k))/(svd.getMinSingularValue()+Math.pow(10,-k));

        Matrix B = Matrix.identity(m,m);
        QRDecomposition qrd = new QRDecomposition(PseudoMatrix);
        PseudoMatrix = qrd.solve(B);
    }

    public Matrix solve(Matrix b){
        Matrix Atb = A.transpose().times(b);
        solution = PseudoMatrix.times(Atb);
        this.X = solution.getArrayCopy();
        return solution;
    }

    public double getcond2(){
        return cond2;
    }
}
