import java.io.*;
/**
 * Test is a class that extends Thread. The work of a for loop is to be shared across
 * a number of different threads.
 * The state information includes: 
 * <ul>
 * <li> The logical thread number.
 * <li> The lowerbound of the for loop.
 * <li> The upperbound of the for loop.
 * <li> An object reference to Data that stores the information used in the calculation.
 * </ul>
*/
   

class Test extends Thread {
 private int lowerBound; // lower bound of the for loop
 private int upperBound; // upper bound of the for loop
 private int threadNo;   // logical thread id
 private Data obj; // a shared object reference
 Test(int id, int lb, int ub,Data o) {
  super(); // as we extend a class, we need to call its constructor
  threadNo = id;
  obj = o;
  lowerBound = lb;
  upperBound = ub;
 }
 public void run() {
  int sum = 0;
  /* Essentially the method merely sums up a portion of the
   * iteration space of the original (see Data class and sum method) 
   * for loop. The idea is that each thread will perform a portion
   * of the work
   */
  for(int i = lowerBound; i < upperBound;i++) {
   sum = sum + obj.A[i];
  }
  obj.partialAnswers[threadNo] = sum; // store the partial answer
 }
};

class Data {
 protected int A[];
 protected int partialAnswers[];
 private Test threads[];
 Data() {
  A = new int[128];
  for(int i =0 ; i < 128;i++) {
   A[i] = 3*i; // initialise the contents of A
        // the actual values arent important
  }
 }
 public int sum() {
  int value = 0;
  for(int i = 0; i < 128; i++) {
   value = value + A[i];
  }
  return value;
 }
public static void main(String args[]) {
 int totalThreads = 0;
 String input = null;
 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 while(totalThreads == 0) {
  try {
   System.out.println("Enter the number of threads (max of 128!!!");
   input = reader.readLine();
   System.out.println(input);
   if(input.compareTo(new String("")) == 0) continue;
   if(input != null) totalThreads  = Integer.parseInt(input);
   if(totalThreads >128 || totalThreads < 0) {
    System.err.println(" 0 < Total Threads <= 128 PLEASE!");
    totalThreads = 0;
   }
  } catch (Exception e) {
   System.err.println(e);
   e.printStackTrace();
   continue;
  }
 }
 System.out.println("Total threads " + totalThreads);
 Data x = new Data(); // create Data object
 System.out.println("CORRECT ANSWER OUTPUT " + x.sum());
 x.partialAnswers = new int[totalThreads];
 for(int i = 0; i < totalThreads;i++) {
  int lb,ub;
  lb = (int)(i * (128.0/totalThreads));
  ub = (int)((i+1) * (128.0/totalThreads));
  if(ub > 128) ub = 128;
  if(i == (totalThreads -1)) ub =128;
  Test tobj = new Test(i,lb,ub,x);
  tobj.start();

 }
 while(Thread.activeCount() != 1);
 int sanity = 0;
 for(int i = 0; i < totalThreads;i++) {
  sanity = sanity + x.partialAnswers[i];
 }
 System.out.println("Im only sane if this is right " +  sanity);
 
}
};


