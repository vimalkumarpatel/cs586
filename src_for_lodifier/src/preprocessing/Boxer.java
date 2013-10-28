/*    */ package preprocessing;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Boxer
/*    */ {
/*    */   public static void doExecBoxer(String p_boxer_path, String p_cc_output, String p_boxer_output)
/*    */     throws IOException
/*    */   {
/* 25 */     OutputStream stdin2 = null;
/* 26 */     InputStream stderr2 = null;
/* 27 */     InputStream stdout2 = null;
/*    */ 
/* 30 */     String[] args = new String[3];
/* 31 */     args[0] = "sh";
/* 32 */     args[1] = "-c";
/* 33 */     args[2] = "";
/* 34 */     args[2] = args[2].concat(p_boxer_path).concat("bin/boxer --input ").concat(p_cc_output).concat(" --output ").concat(p_boxer_output).concat(" --instantiate true --resolve true --semantics drs");
/* 35 */     Process process2 = Runtime.getRuntime().exec(args);
/*    */ 
/* 37 */     stdin2 = process2.getOutputStream();
/* 38 */     stderr2 = process2.getErrorStream();
/* 39 */     stdout2 = process2.getInputStream();
/*    */ 
/* 41 */     stdin2.close();
/*    */ 
/* 44 */     BufferedReader brCleanUp2 = 
/* 45 */       new BufferedReader(new InputStreamReader(stdout2));
/*    */     String line2;
/* 46 */     while ((line2 = brCleanUp2.readLine()) != null)
/*    */     {
/*    */       String line2;
/* 47 */       System.out.println("[Boxer] " + line2);
/*    */     }
/* 49 */     brCleanUp2.close();
/*    */ 
/* 52 */     brCleanUp2 = 
/* 53 */       new BufferedReader(new InputStreamReader(stderr2));
/* 54 */     while ((line2 = brCleanUp2.readLine()) != null) {
/* 55 */       System.out.println("[Boxer] " + line2);
/*    */     }
/* 57 */     brCleanUp2.close();
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     preprocessing.Boxer
 * JD-Core Version:    0.6.2
 */