/*    */ package conf;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class LODifierProperties extends Properties
/*    */ {
/*    */   public LODifierProperties()
/*    */   {
/* 34 */     InputStreamReader in = null;
/*    */     try
/*    */     {
/* 37 */       in = new InputStreamReader(new FileInputStream("LODifier.properties"), "UTF-8");
/* 38 */       load(in);
/*    */     }
/*    */     catch (UnsupportedEncodingException e) {
/* 41 */       e.printStackTrace();
/*    */     }
/*    */     catch (FileNotFoundException e) {
/* 44 */       e.printStackTrace();
/*    */     }
/*    */     catch (IOException e) {
/* 47 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     conf.LODifierProperties
 * JD-Core Version:    0.6.2
 */