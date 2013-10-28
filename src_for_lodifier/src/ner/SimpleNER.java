/*    */ package ner;
/*    */ 
/*    */ public class SimpleNER
/*    */ {
/*    */   public String markNE(String text)
/*    */   {
/* 16 */     if (text.length() >= 4) {
/* 17 */       String[] text_tokens = text.split(" ");
/* 18 */       int upper_count = 0;
/* 19 */       String text_new = "";
/* 20 */       for (int ii = 0; ii <= text_tokens.length - 1; ii++) {
/* 21 */         if (text_tokens[ii].length() > 0) {
/* 22 */           if ((Character.isUpperCase(text_tokens[ii].charAt(0))) && (!text_tokens[ii].contains("_"))) {
/* 23 */             if (upper_count == 0) {
/* 24 */               text_new = text_new.concat(text_tokens[ii] + " ");
/* 25 */               upper_count++;
/*    */             }
/*    */             else {
/* 28 */               text_new = text_new.substring(0, text_new.length() - 1);
/* 29 */               text_new = text_new.concat("_");
/* 30 */               text_new = text_new.concat(text_tokens[ii] + " ");
/* 31 */               upper_count++;
/*    */             }
/*    */           }
/*    */           else {
/* 35 */             text_new = text_new.concat(text_tokens[ii] + " ");
/* 36 */             upper_count = 0;
/*    */           }
/*    */         }
/*    */       }
/* 40 */       return text_new;
/*    */     }
/*    */ 
/* 43 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     ner.SimpleNER
 * JD-Core Version:    0.6.2
 */