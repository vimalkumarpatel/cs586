/*    */ package wn;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class Lemmatizer
/*    */ {
/*    */   public String lemmatize(String cc_output)
/*    */   {
/* 17 */     String lemmatized_input = "ctx_0\n";
/* 18 */     Matcher matcher = Pattern.compile("'(.*)', '(.*)', '(.*)', '.*', '.*'").matcher(cc_output);
/* 19 */     Integer wordcnt = Integer.valueOf(0);
/* 20 */     Integer sentcnt = Integer.valueOf(1);
/* 21 */     while (matcher.find()) {
/* 22 */       String tag = matcher.group(3);
/* 23 */       if ((tag.matches("WRB")) || (tag.startsWith("J"))) {
/* 24 */         tag = "A";
/*    */       }
/* 26 */       tag = tag.substring(0, 1).toLowerCase();
/* 27 */       lemmatized_input = lemmatized_input.concat(matcher.group(2).toLowerCase().concat("#").concat(tag).concat("#w").concat(wordcnt.toString()).concat("#1 "));
/* 28 */       if (matcher.group(2).matches("\\.")) {
/* 29 */         lemmatized_input = lemmatized_input.concat("\nctx_".concat(sentcnt.toString()).concat("\n"));
/* 30 */         sentcnt = Integer.valueOf(sentcnt.intValue() + 1);
/*    */       }
/* 32 */       wordcnt = Integer.valueOf(wordcnt.intValue() + 1);
/*    */     }
/* 34 */     return lemmatized_input;
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     wn.Lemmatizer
 * JD-Core Version:    0.6.2
 */