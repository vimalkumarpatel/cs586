/*    */ package wn;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class UKB
/*    */ {
/*    */   public void doExecUKB(String ukb_path, String ukb_output, String ukb_input)
/*    */     throws IOException
/*    */   {
/* 13 */     OutputStream stdin2 = null;
/* 14 */     InputStream stderr2 = null;
/* 15 */     InputStream stdout2 = null;
/*    */ 
/* 18 */     String[] args = new String[3];
/* 19 */     args[0] = "sh";
/* 20 */     args[1] = "-c";
/* 21 */     args[2] = "";
/* 22 */     args[2] = args[2].concat(ukb_path).concat("bin/ukb_wsd --ppr -K ").concat(ukb_path).concat("bin/wn30.bin -D ").concat(ukb_path).concat("lkb_sources/30/wnet30_dict.txt ".concat(ukb_input).concat(" > ").concat(ukb_output));
/* 23 */     Process process2 = Runtime.getRuntime().exec(args);
/*    */ 
/* 25 */     stdin2 = process2.getOutputStream();
/* 26 */     stderr2 = process2.getErrorStream();
/* 27 */     stdout2 = process2.getInputStream();
/*    */ 
/* 29 */     stdin2.close();
/*    */ 
/* 32 */     BufferedReader brCleanUp2 = 
/* 33 */       new BufferedReader(new InputStreamReader(stdout2));
/*    */     String line2;
/* 34 */     while ((line2 = brCleanUp2.readLine()) != null);
/* 37 */     brCleanUp2.close();
/*    */ 
/* 40 */     brCleanUp2 = 
/* 41 */       new BufferedReader(new InputStreamReader(stderr2));
/* 42 */     while ((line2 = brCleanUp2.readLine()) != null) {
/* 43 */       System.out.println("[Stderr] " + line2);
/*    */     }
/* 45 */     brCleanUp2.close();
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     wn.UKB
 * JD-Core Version:    0.6.2
 */