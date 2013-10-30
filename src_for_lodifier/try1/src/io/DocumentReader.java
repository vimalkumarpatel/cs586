/*    */ package io;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DocumentReader
/*    */ {
/*    */   public String[] readFile(String filename)
/*    */     throws IOException
/*    */   {
/* 17 */     FileReader fileReader = new FileReader(filename);
/* 18 */     BufferedReader bufferedReader = new BufferedReader(fileReader);
/* 19 */     List lines = new ArrayList();
/* 20 */     String line = null;
/* 21 */     while ((line = bufferedReader.readLine()) != null) {
/* 22 */       lines.add(line);
/*    */     }
/* 24 */     bufferedReader.close();
/* 25 */     return (String[])lines.toArray(new String[lines.size()]);
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     io.DocumentReader
 * JD-Core Version:    0.6.2
 */