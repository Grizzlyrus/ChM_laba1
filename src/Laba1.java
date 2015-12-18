///**
// * Created by Admin on 14.10.2015.
// */
//public class Laba1 {
//    public static void main(String args[]){
//        System.out.println("Вариант 12 (Метод Гаусса-Зейделя, октаэдрическая норма, Матрица Гильберта)");
////        int m = 5;
//        int m = 7;
//        int num = 0;
//
//        double det;
//        double cond1 , cond2;
///*
//
////        System.out.println("Матрица A");
////        Matrix A = Matrix.random(m,m);
////        double a[][] = {{10,5,3,2,4},{3,20,4,7,9},{6,2,8,1,3},{7,5,4,12,9},{3,8,6,1,10}};
//        double a[][] = {{5000,10,3,2,1},{10,6000,3,6,5},{6,2,8000,7,8},{6,2,8,9000,10},{2,8,6,4,10000}};
//        Matrix A = new Matrix(a);
////        A.print(m,m);
////        System.out.println("----------------------");
//
//*/
//        double[] x = new double[m];
//        for (int i = 0; i < m ; i++) {
//            x[i]=i+1;
//        }
//        Matrix X = new Matrix(x,m);
////        System.out.println("Матрица X");
////        X.print(m,m);
////        System.out.println("----------------------");
//
//        /*
//
//        Matrix B = A.times(X);
////        System.out.println("Матрица В");
////        B.print(m,B.getColumnDimension());
////        System.out.println("----------------------");
//
//
////      LU - разложение
//        System.out.println("LU - разложение");
//        LUDecomposition lud1 = new LUDecomposition(A,0);
//        Matrix X1 = lud1.solve(B);
////        System.out.println("Решение");
////        X1.print(m,X1.getColumnDimension());
//        System.out.println("Относительная погрешность");
//        for(int i = 0; i<lud1.Error(X,1).length;i++) {
//            System.out.println(lud1.Error(X, 1)[i]);
//        }
//        System.out.println("Количество операций");
//        System.out.println(lud1.getNumber());
//        System.out.println("----------------------");
//
//
////      LU разложение с выбором ведущего элемента по столбцу
//        System.out.println("LU разложение с выбором ведущего элемента по столбцу");
//        LUDecomposition lud2 = new LUDecomposition(A,1);
//        Matrix X2 = lud2.solve(B);
////        System.out.println("Решение");
////        X2.print(m,X2.getColumnDimension());
//        System.out.println("Погрешность");
//        for(int i = 0; i<lud2.Error(X,1).length;i++) {
//            System.out.println(lud2.Error(X, 1)[i]);
//        }
//        System.out.println("Количество операций");
//        num =lud1.getNumber()+5;
//        System.out.println(num);
//
////        int[] piv;
////        piv = lud2.getPivot();
////        for(int i = 0;i<piv.length; i++) {
////            System.out.println(piv[i]);
////        }
//        System.out.println("----------------------");
//
//
////      Метод Зейделя
//        System.out.println("Метод Зейделя");
//        GaussZeidel GZ = new GaussZeidel(A,B,10e-15);
//        Matrix X3 = GZ.solve();
////        System.out.println("Решение");
////        X3.print(m,X3.getColumnDimension());
//        System.out.println("Относительная погрешность");
//        for(int i = 0; i<GZ.Error(X,1).length;i++) {
//            System.out.println(GZ.Error(X, 1)[i]);
//        }
//        System.out.println("Количество операций");
//        System.out.println(GZ.getNumber());
//        System.out.println("----------------------");
//
//
////      Определитель матрицы A
//        System.out.println("Определитель матрицы A");
//        det = lud1.det();
//        System.out.println(det);
//        System.out.println("----------------------");
//
//
////      Число обусловленности матрицы A
//        System.out.println("Число обусловленности матрицы A");
//        cond1 = lud1.cond(1);
//        cond2 = lud2.cond(1);
//        System.out.println(cond1);
//        System.out.println("----------------------");
//
////      Собственные числа матрицы A*AT
//        System.out.println("Собственные числа матрицы A*AT");
//        Matrix AAT = A.times(A.transpose());
//        EigenvalueDecomposition evd = new EigenvalueDecomposition(AAT);
//        double real[]= evd.getRealEigenvalues();
//        for(int i = 0; i<real.length;i++){
//            System.out.println(real[i]);
//        }
//        System.out.println("----------------------");
//        System.out.println("----------------------");
//
//*/
//
//        System.out.println("Плохо обусловленная матрица (матрица Гильберта)");
//        double gilbert[][] = new double[m][m];
//        for(int i = 0;i<m;i++){
//            for (int k=0;k<m;k++){
//                gilbert[i][k] = 1.0/(i+k-1.0+2.0);
//            }
//        }
//        Matrix Gilbert = new Matrix(gilbert);
////        System.out.println("Матрица Гильберта");
////        Gilbert.print(m,m);
//
//        System.out.println("----------------------");
//
////        System.out.println("Матрица X");
////        X.print(m,m);
////        System.out.println("----------------------");
//
//        Matrix B1 = Gilbert.times(X);
////        System.out.println("Матрица В");
////        B1.print(m,B1.getColumnDimension());
////        System.out.println("----------------------");
//
//
////      LU - разложение
//        System.out.println("LU - разложение");
//        LUDecomposition lud3 = new LUDecomposition(Gilbert,0);
//        Matrix X4 = lud3.solve(B1);
////        System.out.println("Решение");
////        X4.print(m,X4.getColumnDimension());
//        System.out.println("Относительная погрешность");
//        for(int i = 0; i<lud3.Error(X,1).length;i++) {
//            System.out.println(lud3.Error(X, 1)[i]);
//        }
//        System.out.println("Количество операций");
//        System.out.println(lud3.getNumber());
//        System.out.println("----------------------");
//
//
////      LU разложение с выбором ведущего элемента по столбцу
//        System.out.println("LU разложение с выбором ведущего элемента по столбцу");
//        LUDecomposition lud4 = new LUDecomposition(Gilbert,1);
//        Matrix X5 = lud4.solve(B1);
////        System.out.println("Решение");
////        X5.print(m,X5.getColumnDimension());
//        System.out.println("Относительная погрешность");
//        for(int i = 0; i<lud4.Error(X,1).length;i++) {
//            System.out.println(lud4.Error(X, 1)[i]);
//        }
//        System.out.println("Количество операций");
//        num =lud4.getNumber()+5;
//        System.out.println(num);
//        System.out.println("----------------------");
//
//
//
////      Метод Зейделя
//        System.out.println("Метод Зейделя");
//        System.out.println("Не выполняется достаточное условие сходимости");
//
//        /*
//        GaussZeidel GZ1 = new GaussZeidel(Gilbert,B1,0.0001);
//        Matrix X6 = GZ1.solve();
////        System.out.println("Решение");
////        X6.print(m,X6.getColumnDimension());
//        System.out.println("Погрешность");
//        for(int i = 0; i<GZ1.Error(X,1).length;i++) {
//            System.out.println(GZ.Error(X, 1)[i]);
//        }
//        System.out.println("Количество операций");
//        System.out.println(GZ1.getNumber());
//        */
//        System.out.println("----------------------");
//
//
//
////      Определитель матрицы A
//        System.out.println("Определитель матрицы A");
//        det = lud3.det();
//        System.out.println(det);
//        System.out.println("----------------------");
//
//
////      Число обусловленности матрицы A
//        System.out.println("Число обусловленности матрицы A");
//        cond1 = lud3.cond(1);
//        cond2 = lud4.cond(1);
//        System.out.println(cond1);
//        System.out.println("----------------------");
//
////      Собственные числа матрицы A*AT
//        System.out.println("Собственные числа матрицы A*AT");
//        Matrix AAT1 = Gilbert.times(Gilbert.transpose());
//        EigenvalueDecomposition evd1 = new EigenvalueDecomposition(AAT1);
//        double real1[]= evd1.getRealEigenvalues();
//        for(int i = 0; i<real1.length;i++){
//            System.out.println(real1[i]);
//        }
//
//
////            Matrix aaa = Matrix.genMatrix(5,3);
////            aaa.print(5,2);
////            CholeskyDecomposition chd = new CholeskyDecomposition(aaa);
////            chd.getL().print(5,2);
////            Matrix b= new Matrix(5,1);
////            for(int i = 0;i<5;i++){
////                    b.set(i,0,i+1);
////            }
////
////            Matrix xx = chd.solve(b);
////            xx.print(5,4);
//
//    }
//}
