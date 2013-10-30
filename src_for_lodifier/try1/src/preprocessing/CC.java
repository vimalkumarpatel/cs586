/*    */ package preprocessing;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class CC
/*    */ {
/*    */   public static void doExecCC(String p_cc_input, String p_boxer_path, String p_cc_out_path)
/*    */     throws IOException
/*    */   {
/* 26 */     OutputStream stdin1 = null;
/* 27 */     InputStream stderr1 = null;
/* 28 */     InputStream stdout1 = null;
/* 29 */     p_cc_input = p_cc_input.substring(0, p_cc_input.length() - 3);
/* 30 */     String[] args = new String[3];
/*    */ 
/* 32 */     args[0] = "sh";
/* 33 */     args[1] = "-c";
/* 34 */     args[2] = "echo \"";
/*    */ 
/* 39 */     args[2] = args[2].concat(p_cc_input).concat("\" | ").concat(p_boxer_path).concat("bin/candc --models ").concat(p_boxer_path).concat("models/boxer/ --candc-printer boxer --output ").concat(p_cc_out_path);
/*    */ 
/* 41 */     Process process1 = Runtime.getRuntime().exec(args);
/*    */ 
/* 44 */     stdin1 = process1.getOutputStream();
/* 45 */     stderr1 = process1.getErrorStream();
/* 46 */     stdout1 = process1.getInputStream();
/*    */ 
/* 48 */     stdin1.close();
/*    */ 
/* 51 */     BufferedReader brCleanUp1 = 
/* 52 */       new BufferedReader(new InputStreamReader(stdout1));
/*    */     String line1;
/* 53 */     while ((line1 = brCleanUp1.readLine()) != null)
/*    */     {
/*    */       String line1;
/* 54 */       System.out.println("[C&C] " + line1);
/*    */     }
/* 56 */     brCleanUp1.close();
/*    */ 
/* 59 */     brCleanUp1 = 
/* 60 */       new BufferedReader(new InputStreamReader(stderr1));
/* 61 */     while ((line1 = brCleanUp1.readLine()) != null) {
/* 62 */       System.out.println("[C&C] " + line1);
/*    */     }
/* 64 */     brCleanUp1.close();
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     preprocessing.CC
 * JD-Core Version:    0.6.2
 */