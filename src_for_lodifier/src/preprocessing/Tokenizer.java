/*    */ package preprocessing;
/*    */ 
/*    */ import ui.UserInterface;
/*    */ 
/*    */ public class Tokenizer
/*    */ {
/*    */   public String[] tokenize(String s)
/*    */   {
/* 18 */     UserInterface ui = new UserInterface();
/* 19 */     String[] stories = s.split("%%%");
/* 20 */     for (int i = 0; i <= stories.length - 1; i++) {
/* 21 */       stories[i] = stories[i].replaceAll("\n", " ");
/* 22 */       stories[i] = stories[i].replace("a.m.", "am");
/* 23 */       stories[i] = stories[i].replace("p.m.", "pm");
/* 24 */       stories[i] = stories[i].replace("...", " .\n");
/* 25 */       stories[i] = stories[i].replace("..", " .\n");
/* 26 */       stories[i] = stories[i].replaceAll("\\.( |$)", " \\.\n");
/* 27 */       stories[i] = stories[i].replace(":", " : ");
/* 28 */       stories[i] = stories[i].replace("_", " ");
/* 29 */       stories[i] = stories[i].replaceAll("! ", " !\n");
/* 30 */       stories[i] = stories[i].replaceAll("\\? ", " \\?\n");
/* 31 */       stories[i] = stories[i].replace(",", " ,");
/* 32 */       stories[i] = stories[i].replaceAll("\\|", " \\.\n");
/* 33 */       stories[i] = stories[i].replace("`", "");
/* 34 */       stories[i] = stories[i].replace("´", "");
/* 35 */       stories[i] = stories[i].replaceAll("'", "");
/* 36 */       stories[i] = stories[i].replaceAll("\\(", ", ");
/* 37 */       stories[i] = stories[i].replaceAll("\\)", " ,");
/* 38 */       stories[i] = stories[i].replace(", .", ".");
/* 39 */       stories[i] = stories[i].replace(", ,", ",");
/* 40 */       stories[i] = stories[i].replace(". .", ".");
/* 41 */       stories[i] = stories[i].replace("  ", " ");
/*    */     }
/*    */ 
/* 44 */     return stories;
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     preprocessing.Tokenizer
 * JD-Core Version:    0.6.2
 */