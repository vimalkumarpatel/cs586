/*    */ package io;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class DocumentWriter
/*    */ {
/*    */   public void writeFile(String file, String content)
/*    */     throws IOException
/*    */   {
/* 10 */     if (file.length() != 0) {
/* 11 */       BufferedWriter out = new BufferedWriter(new FileWriter(file));
/* 12 */       out.write(content);
/* 13 */       out.close();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     io.DocumentWriter
 * JD-Core Version:    0.6.2
 */