/*      */ package rdf;
/*      */ 
/*      */ import data.Story;
/*      */ import data.Triples;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ public class RelationshipExtraction
/*      */ {
/*   23 */   private static ArrayList<String> uris = new ArrayList();
/*      */ 
/*      */   public static void addURIs(String uri)
/*      */   {
/*   30 */     uris.add(uri);
/*      */   }
/*      */ 
/*      */   public ArrayList<String> getURIs() {
/*   34 */     return uris;
/*      */   }
/*      */ 
/*      */   public void deleteUris() {
/*   38 */     uris = new ArrayList();
/*      */   }
/*      */ 
/*      */   public static String cleanBoxerOutput(String input)
/*      */   {
/*   47 */     String rdf = input;
/*   48 */     rdf = rdf.replace("&", "and");
/*   49 */     rdf = rdf.replace("'", "");
/*   50 */     rdf = rdf.replace(".", "");
/*   51 */     return rdf;
/*      */   }
/*      */ 
/*      */   public static String writePrefixes()
/*      */   {
/*   59 */     String s = "";
/*   60 */     s = s.concat("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n@prefix owl: <http://www.w3.org/2002/07/owl#> .\n@prefix xmls: <http://www.w3.org/2001/XMLSchema#> .\n@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n@prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> .\n@prefix ex: <http://example.org/> .\n@prefix ne: <http://example.org/ne/> .\n@prefix drsclass: <http://example.org/drsclass/> .\n@prefix class: <http://example.org/class/> .\n@prefix drsrel: <http://example.org/drsrel/> .\n@prefix rel: <http://example.org/rel/> .\n@prefix dbpedia: <http://dbpedia.org/resource/> .\n@prefix wn30: <http://purl.org/vocabularies/princeton/wn30/> .\n@prefix wn20schema: <http://www.w3.org/2006/03/wn/wn20/schema/> .\n@prefix dcterms: <http://purl.org/dc/terms/> .\n");
/*      */ 
/*   75 */     return s;
/*      */   }
/*      */ 
/*      */   public static String writePrefixes2()
/*      */   {
/*   83 */     String s = "";
/*   84 */     s = s.concat("@prefix rdf: <<a href=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">http://www.w3.org/1999/02/22-rdf-syntax-ns#</a>> .\n@prefix owl: <<a href=\"http://www.w3.org/2001/XMLSchema#\">http://www.w3.org/2001/XMLSchema#</a>> .\n@prefix xmls: <<a href=\"http://www.w3.org/2002/07/owl#\">http://www.w3.org/2002/07/owl#</a>> .\n@prefix foaf: <<a href=\"http://xmlns.com/foaf/0.1/\">http://xmlns.com/foaf/0.1/</a>> .\n@prefix geo: <<a href=\"http://www.w3.org/2003/01/geo/wgs84_pos#\">http://www.w3.org/2003/01/geo/wgs84_pos#</a>> .\n@prefix ex: <http://example.org/> .\n@prefix ne: <http://example.org/ne/> .\n@prefix drsclass: <http://example.org/drsclass/> .\n@prefix class: <http://example.org/class/> .\n@prefix drsrel: <http://example.org/drsrel/> .\n@prefix rel: <http://example.org/rel/> .\n@prefix dbpedia: <<a href=\"http://dbpedia.org/resource/\">http://dbpedia.org/resource/</a>> .\n@prefix wn30: <<a href=\"http://purl.org/vocabularies/princeton/wn30/\">http://purl.org/vocabularies/princeton/wn30/</a>> .\n@prefix wn20schema: <<a href=\"http://www.w3.org/2006/03/wn/wn20/schema/\">http://www.w3.org/2006/03/wn/wn20/schema/</a>> .\n@prefix dcterms: <<a href=\"http://purl.org/dc/terms/\">http://purl.org/dc/terms/</a>> .\n");
/*      */ 
/*   99 */     return s;
/*      */   }
/*      */ 
/*      */   public static ArrayList tokenize_String(String textAreaValue)
/*      */   {
/*  109 */     String[] pieces = textAreaValue.split("");
/*  110 */     return new ArrayList(Arrays.asList(pieces));
/*      */   }
/*      */ 
/*      */   public static int find_Splitindex(String input, String openingbracket, String closingbracket)
/*      */   {
/*  122 */     int index = 0;
/*  123 */     int bracketcount = 0;
/*  124 */     int openingbracketcount = 0;
/*  125 */     ArrayList alist = new ArrayList();
/*  126 */     alist = tokenize_String(input);
/*      */ 
/*  129 */     for (Iterator it = alist.iterator(); it.hasNext(); ) {
/*  130 */       String str = (String)it.next();
/*  131 */       if (str.equals(openingbracket)) {
/*  132 */         bracketcount++;
/*  133 */         openingbracketcount++;
/*      */       }
/*  135 */       else if ((str.equals(closingbracket)) && (openingbracketcount > 0)) {
/*  136 */         bracketcount--;
/*  137 */         if (bracketcount == 0) {
/*      */           break;
/*      */         }
/*      */       }
/*  141 */       index++;
/*      */     }
/*  143 */     if (bracketcount == 0) {
/*  144 */       return index;
/*      */     }
/*      */ 
/*  147 */     return 0;
/*      */   }
/*      */ 
/*      */   public static String extractDRSStructure(String boxer_output)
/*      */   {
/*  157 */     String drs = "";
/*      */ 
/*  160 */     Pattern replace = Pattern.compile(":");
/*  161 */     Matcher matcher2 = replace.matcher(boxer_output);
/*  162 */     String save = matcher2.replaceAll("=");
/*      */ 
/*  164 */     Pattern repl = Pattern.compile("[ \t\n\f\r]");
/*  165 */     Matcher match2 = repl.matcher(save);
/*  166 */     String save2 = match2.replaceAll(" ");
/*      */ 
/*  168 */     Pattern boxer_pattern = Pattern.compile("],    (drs(.*)).");
/*  169 */     Matcher boxer_matcher = boxer_pattern.matcher(save2);
/*      */ 
/*  171 */     while (boxer_matcher.find()) {
/*  172 */       drs = boxer_matcher.group(1);
/*      */     }
/*      */ 
/*  175 */     if (drs.isEmpty()) {
/*  176 */       Pattern boxer_pattern2 = Pattern.compile("],    (s?merge|alfa)(.*)");
/*  177 */       Matcher boxer_matcher2 = boxer_pattern2.matcher(save2);
/*      */ 
/*  179 */       while (boxer_matcher2.find()) {
/*  180 */         drs = boxer_matcher2.group(2);
/*      */       }
/*      */     }
/*      */ 
/*  184 */     drs = drs.replaceAll("\\).id\\([0-9]*,[0-9]*\\)", "");
/*  185 */     return drs;
/*      */   }
/*      */ 
/*      */   public static String[] split_DRS(String p_all_drs)
/*      */   {
/*  196 */     int idx1 = find_Splitindex(p_all_drs, "(", ")");
/*  197 */     String drs_1 = p_all_drs.substring(0, idx1);
/*  198 */     String drs_2 = "";
/*  199 */     if (p_all_drs.length() - drs_1.length() > 8) {
/*  200 */       drs_2 = p_all_drs.substring(idx1 + 1);
/*      */     }
/*  202 */     String[] drs_both = { drs_1, drs_2 };
/*      */ 
/*  204 */     return drs_both;
/*      */   }
/*      */ 
/*      */   public static String[] split_DRS_recursively(String p_all_drs, ArrayList<String> p_temp)
/*      */   {
/*      */     String[] rest;
/*      */     do
/*      */     {
/*  216 */       rest = split_DRS(p_all_drs);
/*  217 */       p_temp.add(rest[0]);
/*  218 */       if (rest[1].startsWith("(")) {
/*  219 */         p_all_drs = rest[1].substring(1);
/*      */       }
/*  221 */       else if (rest[1].startsWith("smerge(")) {
/*  222 */         p_all_drs = rest[1].substring(7);
/*      */       }
/*  224 */       else if (rest[1].startsWith("merge(")) {
/*  225 */         p_all_drs = rest[1].substring(6);
/*      */       }
/*  227 */       else if (rest[1].startsWith("alfa(")) {
/*  228 */         p_all_drs = rest[1].substring(5);
/*      */       }
/*      */       else
/*  231 */         p_all_drs = rest[1];
/*      */     }
/*  215 */     while (
/*  234 */       rest[1].contains("drs("));
/*      */ 
/*  236 */     String[] all_drs = new String[p_temp.size()];
/*  237 */     for (int i = 0; i <= all_drs.length - 1; i++) {
/*  238 */       all_drs[i] = ((String)p_temp.get(i));
/*      */     }
/*  240 */     return all_drs;
/*      */   }
/*      */ 
/*      */   public static ArrayList extractConditions(String condition)
/*      */   {
/*  249 */     Story story = new Story();
/*  250 */     Triples triples = new Triples();
/*  251 */     int feed_nr = Story.getStoryNr();
/*  252 */     ArrayList list = new ArrayList();
/*  253 */     String s = "";
/*  254 */     String number = "";
/*      */ 
/*  256 */     if (condition.contains("=")) {
/*  257 */       String[] cond = condition.split("=");
/*  258 */       s = cond[1];
/*      */     }
/*      */     else {
/*  261 */       s = condition;
/*      */     }
/*      */ 
/*  264 */     if (s.startsWith("rel")) {
/*  265 */       if ((condition.contains("agent,0")) || (condition.contains("patient,0")) || (condition.contains("rel,0")) || (condition.contains("loc_rel,0")) || (condition.contains("role,0")) || (condition.contains("member,0")) || (condition.contains("theme,0"))) {
/*  266 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  267 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  268 */         while (matcher_named.find()) {
/*  269 */           String var2 = matcher_named.group(3);
/*  270 */           String var1 = matcher_named.group(4);
/*  271 */           String drsrel = matcher_named.group(5);
/*  272 */           list.add("_:var" + feed_nr + var1);
/*  273 */           Triples.setVariable_List("_:var" + feed_nr + var1);
/*  274 */           list.add("drsrel:" + drsrel);
/*  275 */           list.add("_:var" + feed_nr + var2);
/*  276 */           Triples.setVariable_List("_:var" + feed_nr + var2);
/*      */         }
/*      */       }
/*      */       else {
/*  280 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  281 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  282 */         while (matcher_named.find()) {
/*  283 */           String var2 = matcher_named.group(3);
/*  284 */           String var1 = matcher_named.group(4);
/*  285 */           String rel = matcher_named.group(5);
/*  286 */           list.add("_:var" + feed_nr + var1);
/*  287 */           Triples.setVariable_List("_:var" + feed_nr + var1);
/*  288 */           if (rel.equals("=")) {
/*  289 */             list.add("owl:sameAs");
/*      */           }
/*      */           else {
/*  292 */             list.add("rel:" + rel);
/*      */           }
/*  294 */           list.add("_:var" + feed_nr + var2);
/*  295 */           Triples.setVariable_List("_:var" + feed_nr + var2);
/*      */         }
/*      */       }
/*      */     }
/*  299 */     else if (s.startsWith("pred")) {
/*  300 */       if ((condition.contains("topic,a,1")) || (condition.contains("thing,n,12")) || (condition.contains("person,n,1")) || (condition.contains("event,n,1")) || (condition.contains("group,n,1")) || (condition.contains("reason,n,2")) || (condition.contains("manner,n,2")) || (condition.contains("proposition,n,1")) || (condition.contains("unit_of_time,n,1")) || (condition.contains("location,n,1")) || (condition.contains("quantity,n,1")) || (condition.contains("amount,n,3")) || (condition.contains("degree,n,1")) || (condition.contains("age,n,1")) || (condition.contains("neuter,a,0")) || (condition.contains("male,a,0")) || (condition.contains("female,a,0")) || (condition.contains("base,v,2")) || (condition.contains("bear,v,2"))) {
/*  301 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  302 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  303 */         while (matcher_named.find()) {
/*  304 */           String var = matcher_named.group(3);
/*  305 */           String drsclass = matcher_named.group(4);
/*  306 */           drsclass = cleanBoxerOutput(drsclass);
/*  307 */           list.add("_:var" + feed_nr + var);
/*  308 */           Triples.setVariable_List("_:var" + feed_nr + var);
/*  309 */           list.add("rdf:type");
/*  310 */           list.add("drsclass:" + drsclass);
/*      */         }
/*      */       }
/*      */       else {
/*  314 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  315 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  316 */         while (matcher_named.find()) {
/*  317 */           String var = matcher_named.group(3);
/*  318 */           String clas = matcher_named.group(4);
/*  319 */           clas = cleanBoxerOutput(clas);
/*  320 */           list.add("_:var" + feed_nr + var);
/*  321 */           Triples.setVariable_List("_:var" + feed_nr + var);
/*  322 */           list.add("rdf:type");
/*  323 */           list.add("class:" + clas);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  328 */     else if (s.startsWith("named")) {
/*  329 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  330 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  331 */       while (matcher_named.find()) {
/*  332 */         String var = matcher_named.group(3);
/*  333 */         String nam = matcher_named.group(4);
/*  334 */         nam = cleanBoxerOutput(nam);
/*  335 */         String typ = matcher_named.group(5);
/*  336 */         list.add("_:var" + feed_nr + var);
/*  337 */         Triples.setVariable_List("_:var" + feed_nr + var);
/*  338 */         list.add("drsclass:named");
/*  339 */         list.add("ne:" + nam);
/*      */ 
/*  341 */         if (typ.equals("org")) {
/*  342 */           list.add("ne:" + nam);
/*  343 */           list.add("rdf:type");
/*  344 */           list.add("foaf:Organization");
/*      */         }
/*  346 */         else if (typ.equals("per")) {
/*  347 */           list.add("ne:" + nam);
/*  348 */           list.add("rdf:type");
/*  349 */           list.add("foaf:Person");
/*      */         }
/*  351 */         else if (typ.equals("ttl")) {
/*  352 */           list.add("_:var" + feed_nr + var);
/*  353 */           list.add("foaf:title");
/*  354 */           list.add("ne:" + nam);
/*      */         }
/*  356 */         else if (typ.equals("fst")) {
/*  357 */           list.add("_:var" + feed_nr + var);
/*  358 */           list.add("foaf:firstName");
/*  359 */           list.add("ne:" + nam);
/*      */         }
/*  361 */         else if (typ.equals("sur")) {
/*  362 */           list.add("_:var" + feed_nr + var);
/*  363 */           list.add("foaf:surname");
/*  364 */           list.add("ne:" + nam);
/*      */         }
/*  366 */         else if (typ.equals("nam")) {
/*  367 */           list.add("_:var" + feed_nr + var);
/*  368 */           list.add("foaf:name");
/*  369 */           list.add("ne:" + nam);
/*      */         }
/*  371 */         else if (typ.equals("location")) {
/*  372 */           list.add("_:var" + feed_nr + var);
/*  373 */           list.add("geo:Point");
/*  374 */           list.add("ne:" + nam);
/*      */         }
/*  376 */         else if (typ.equals("url")) {
/*  377 */           list.add("_:var" + feed_nr + var);
/*  378 */           list.add("foaf:homepage");
/*  379 */           list.add("ne:" + nam);
/*      */         }
/*  381 */         else if (typ.equals("email")) {
/*  382 */           list.add("_:var" + feed_nr + var);
/*  383 */           list.add("foaf:mbox");
/*  384 */           list.add("ne:" + nam);
/*      */         }
/*      */         else {
/*  387 */           list.add("ne:" + nam);
/*  388 */           list.add("rdf:type");
/*  389 */           list.add("drsclass:" + typ);
/*      */         }
/*      */       }
/*      */     }
/*  393 */     else if (s.startsWith("timex")) {
/*  394 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(date|time.*)((.*=.*,)?.*='(.*)',.*='(.*)',.*='(.*)'))");
/*  395 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  396 */       while (matcher_named.find()) {
/*  397 */         String var = matcher_named.group(3);
/*  398 */         String typ = matcher_named.group(4);
/*  399 */         String year = matcher_named.group(7);
/*  400 */         year = cleanBoxerOutput(year);
/*  401 */         String month = matcher_named.group(8);
/*  402 */         month = cleanBoxerOutput(month);
/*  403 */         String day = matcher_named.group(9);
/*  404 */         day = cleanBoxerOutput(day);
/*  405 */         list.add("_:var" + feed_nr + var);
/*  406 */         Triples.setVariable_List("_:var" + feed_nr + var);
/*  407 */         if (typ.contains("date")) {
/*  408 */           list.add("xmls:date");
/*  409 */           list.add("\"" + year + "-" + month + "-" + day + "\"");
/*      */         }
/*  411 */         if (typ.contains("time")) {
/*  412 */           list.add("xmls:time");
/*  413 */           list.add("\"" + year + ":" + month + ":" + day + "\"");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*  419 */     else if (s.startsWith("card")) {
/*  420 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*))");
/*  421 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  422 */       while (matcher_named.find()) {
/*  423 */         String var = matcher_named.group(3);
/*  424 */         number = matcher_named.group(4);
/*  425 */         number = cleanBoxerOutput(number);
/*  426 */         list.add("_:var" + feed_nr + var);
/*  427 */         Triples.setVariable_List("_:var" + feed_nr + var);
/*  428 */         list.add("xmls:decimal");
/*  429 */         list.add("\"" + number + "\"");
/*      */       }
/*      */ 
/*      */     }
/*  433 */     else if (s.startsWith("eq")) {
/*  434 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x[0-9]*),(x[0-9]*))");
/*  435 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  436 */       while (matcher_named.find()) {
/*  437 */         String var1 = matcher_named.group(3);
/*  438 */         String var2 = matcher_named.group(4);
/*  439 */         list.add("_:var" + feed_nr + var1);
/*  440 */         Triples.setVariable_List("_:var" + feed_nr + var1);
/*  441 */         list.add("owl:sameAs");
/*  442 */         list.add("_:var" + feed_nr + var2);
/*  443 */         Triples.setVariable_List("_:var" + feed_nr + var2);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  448 */       String prefix = "_:var" + feed_nr + "x";
/*  449 */       list = extractComplexConditions(condition, prefix);
/*      */     }
/*      */ 
/*  454 */     return list;
/*      */   }
/*      */ 
/*      */   public static ArrayList extractConditions(String condition, String start_var)
/*      */   {
/*  466 */     ArrayList list = new ArrayList();
/*  467 */     String s = "";
/*  468 */     String answertype = "";
/*  469 */     Story story = new Story();
/*  470 */     Triples triples = new Triples();
/*  471 */     int feed_nr = Story.getStoryNr();
/*      */ 
/*  473 */     if (condition.contains("=")) {
/*  474 */       String[] cond = condition.split("=");
/*  475 */       s = cond[1];
/*      */     }
/*      */     else {
/*  478 */       s = condition;
/*      */     }
/*      */ 
/*  481 */     if (s.startsWith("rel")) {
/*  482 */       if ((condition.contains("agent,0")) || (condition.contains("patient,0")) || (condition.contains("rel,0")) || (condition.contains("loc_rel,0")) || (condition.contains("role,0")) || (condition.contains("member,0")) || (condition.contains("theme,0"))) {
/*  483 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(x.*),(.*),(.*))");
/*  484 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  485 */         while (matcher_named.find()) {
/*  486 */           String var2 = matcher_named.group(3);
/*  487 */           String var1 = matcher_named.group(4);
/*  488 */           String drsrel = matcher_named.group(5);
/*  489 */           String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  490 */           list.add(start_var);
/*  491 */           list.add("ex:conjunct");
/*  492 */           list.add(tripel_var);
/*  493 */           list.add(tripel_var);
/*  494 */           list.add("rdf:subject");
/*  495 */           list.add("_:var" + feed_nr + var1);
/*  496 */           Triples.setVariable_List("_:var" + feed_nr + var1);
/*  497 */           Triples.setVariable_List(tripel_var);
/*  498 */           list.add(tripel_var);
/*  499 */           list.add("rdf:predicate");
/*  500 */           list.add("rel:" + drsrel);
/*  501 */           list.add(tripel_var);
/*  502 */           list.add("rdf:object");
/*  503 */           list.add("_:var" + feed_nr + var2);
/*  504 */           Triples.setVariable_List("_:var" + feed_nr + var2);
/*      */         }
/*      */       }
/*      */       else {
/*  508 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(x.*),(.*),(.*))");
/*  509 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  510 */         while (matcher_named.find()) {
/*  511 */           String var2 = matcher_named.group(3);
/*  512 */           String var1 = matcher_named.group(4);
/*  513 */           String rel = matcher_named.group(5);
/*  514 */           String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  515 */           list.add(start_var);
/*  516 */           list.add("ex:conjunct");
/*  517 */           list.add(tripel_var);
/*  518 */           list.add(tripel_var);
/*  519 */           list.add("rdf:subject");
/*  520 */           list.add("_:var" + feed_nr + var1);
/*  521 */           Triples.setVariable_List("_:var" + feed_nr + var1);
/*  522 */           Triples.setVariable_List(tripel_var);
/*  523 */           list.add(tripel_var);
/*  524 */           list.add("rdf:predicate");
/*  525 */           if (rel.equals("=")) {
/*  526 */             list.add("owl:sameAs");
/*      */           }
/*      */           else {
/*  529 */             list.add("rel:" + rel);
/*      */           }
/*  531 */           list.add(tripel_var);
/*  532 */           list.add("rdf:object");
/*  533 */           list.add("_:var" + feed_nr + var2);
/*  534 */           Triples.setVariable_List("_:var" + feed_nr + var2);
/*      */         }
/*      */       }
/*      */     }
/*  538 */     else if (s.startsWith("pred")) {
/*  539 */       if ((condition.contains("topic,a,1")) || (condition.contains("thing,n,12")) || (condition.contains("person,n,1")) || (condition.contains("event,n,1")) || (condition.contains("group,n,1")) || (condition.contains("reason,n,2")) || (condition.contains("manner,n,2")) || (condition.contains("proposition,n,1")) || (condition.contains("unit_of_time,n,1")) || (condition.contains("location,n,1")) || (condition.contains("quantity,n,1")) || (condition.contains("amount,n,3")) || (condition.contains("degree,n,1")) || (condition.contains("age,n,1")) || (condition.contains("neuter,a,0")) || (condition.contains("male,a,0")) || (condition.contains("female,a,0")) || (condition.contains("base,v,2")) || (condition.contains("bear,v,2"))) {
/*  540 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  541 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  542 */         while (matcher_named.find()) {
/*  543 */           String var = matcher_named.group(3);
/*  544 */           String drsclass = matcher_named.group(4);
/*  545 */           drsclass = cleanBoxerOutput(drsclass);
/*  546 */           String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  547 */           list.add(start_var);
/*  548 */           list.add("ex:conjunct");
/*  549 */           list.add(tripel_var);
/*  550 */           list.add(tripel_var);
/*  551 */           list.add("rdf:subject");
/*  552 */           list.add("_:var" + feed_nr + var);
/*  553 */           Triples.setVariable_List("_:var" + feed_nr + var);
/*  554 */           Triples.setVariable_List(tripel_var);
/*  555 */           list.add(tripel_var);
/*  556 */           list.add("rdf:predicate");
/*  557 */           list.add("rdf:type");
/*  558 */           list.add(tripel_var);
/*  559 */           list.add("rdf:object");
/*  560 */           list.add("drsclass:" + drsclass);
/*      */         }
/*      */       }
/*      */       else {
/*  564 */         Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  565 */         Matcher matcher_named = pattern_named.matcher(condition);
/*  566 */         while (matcher_named.find()) {
/*  567 */           String var = matcher_named.group(3);
/*  568 */           String clas = matcher_named.group(4);
/*  569 */           clas = cleanBoxerOutput(clas);
/*  570 */           String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  571 */           list.add(start_var);
/*  572 */           list.add("ex:conjunct");
/*  573 */           list.add(tripel_var);
/*  574 */           list.add(tripel_var);
/*  575 */           list.add("rdf:subject");
/*  576 */           list.add("_:var" + feed_nr + var);
/*  577 */           Triples.setVariable_List("_:var" + feed_nr + var);
/*  578 */           Triples.setVariable_List(tripel_var);
/*  579 */           list.add(tripel_var);
/*  580 */           list.add("rdf:predicate");
/*  581 */           list.add("rdf:type");
/*  582 */           list.add(tripel_var);
/*  583 */           list.add("rdf:object");
/*  584 */           list.add("drsclass:" + clas);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  589 */     else if (s.startsWith("named")) {
/*  590 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*),(.*))");
/*  591 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  592 */       while (matcher_named.find()) {
/*  593 */         String var = matcher_named.group(3);
/*  594 */         String nam = matcher_named.group(4);
/*  595 */         nam = cleanBoxerOutput(nam);
/*  596 */         String typ = matcher_named.group(5);
/*  597 */         String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  598 */         list.add(start_var);
/*  599 */         list.add("ex:conjunct");
/*  600 */         list.add(tripel_var);
/*  601 */         list.add(tripel_var);
/*  602 */         list.add("rdf:subject");
/*  603 */         list.add("_:var" + feed_nr + var);
/*  604 */         Triples.setVariable_List("_:var" + feed_nr + var);
/*  605 */         Triples.setVariable_List(tripel_var);
/*  606 */         list.add(tripel_var);
/*  607 */         list.add("rdf:predicate");
/*  608 */         list.add("drsclass:named");
/*  609 */         list.add(tripel_var);
/*  610 */         list.add("rdf:object");
/*  611 */         list.add("ne:" + nam);
/*      */ 
/*  613 */         if (typ.equals("org")) {
/*  614 */           list.add(tripel_var);
/*  615 */           list.add("rdf:subject");
/*  616 */           list.add("ne:" + nam);
/*  617 */           list.add(tripel_var);
/*  618 */           list.add("rdf:predicate");
/*  619 */           list.add("rdf:type");
/*  620 */           list.add(tripel_var);
/*  621 */           list.add("rdf:object");
/*  622 */           list.add("foaf:Organization");
/*      */         }
/*  624 */         else if (typ.equals("per")) {
/*  625 */           list.add(tripel_var);
/*  626 */           list.add("rdf:subject");
/*  627 */           list.add("ne:" + nam);
/*  628 */           list.add(tripel_var);
/*  629 */           list.add("rdf:predicate");
/*  630 */           list.add("rdf:type");
/*  631 */           list.add(tripel_var);
/*  632 */           list.add("rdf:object");
/*  633 */           list.add("foaf:Person");
/*      */         }
/*  635 */         else if (typ.equals("ttl")) {
/*  636 */           list.add(tripel_var);
/*  637 */           list.add("rdf:subject");
/*  638 */           list.add("_:var" + feed_nr + var);
/*  639 */           list.add(tripel_var);
/*  640 */           list.add("rdf:predicate");
/*  641 */           list.add("foaf:title");
/*  642 */           list.add(tripel_var);
/*  643 */           list.add("rdf:object");
/*  644 */           list.add("ne:" + nam);
/*      */         }
/*  646 */         else if (typ.equals("fst")) {
/*  647 */           list.add(tripel_var);
/*  648 */           list.add("rdf:subject");
/*  649 */           list.add("_:var" + feed_nr + var);
/*  650 */           list.add(tripel_var);
/*  651 */           list.add("rdf:predicate");
/*  652 */           list.add("foaf:firstName");
/*  653 */           list.add(tripel_var);
/*  654 */           list.add("rdf:object");
/*  655 */           list.add("ne:" + nam);
/*      */         }
/*  657 */         else if (typ.equals("sur")) {
/*  658 */           list.add(tripel_var);
/*  659 */           list.add("rdf:subject");
/*  660 */           list.add("_:var" + feed_nr + var);
/*  661 */           list.add(tripel_var);
/*  662 */           list.add("rdf:predicate");
/*  663 */           list.add("foaf:surname");
/*  664 */           list.add(tripel_var);
/*  665 */           list.add("rdf:object");
/*  666 */           list.add("ne:" + nam);
/*      */         }
/*  668 */         else if (typ.equals("nam")) {
/*  669 */           list.add(tripel_var);
/*  670 */           list.add("rdf:subject");
/*  671 */           list.add("_:var" + feed_nr + var);
/*  672 */           list.add(tripel_var);
/*  673 */           list.add("rdf:predicate");
/*  674 */           list.add("foaf:name");
/*  675 */           list.add(tripel_var);
/*  676 */           list.add("rdf:object");
/*  677 */           list.add("ne:" + nam);
/*      */         }
/*  679 */         else if (typ.equals("location")) {
/*  680 */           list.add(tripel_var);
/*  681 */           list.add("rdf:subject");
/*  682 */           list.add("_:var" + feed_nr + var);
/*  683 */           list.add(tripel_var);
/*  684 */           list.add("rdf:predicate");
/*  685 */           list.add("geo:Point");
/*  686 */           list.add(tripel_var);
/*  687 */           list.add("rdf:object");
/*  688 */           list.add("ne:" + nam);
/*      */         }
/*  690 */         else if (typ.equals("url")) {
/*  691 */           list.add(tripel_var);
/*  692 */           list.add("rdf:subject");
/*  693 */           list.add("_:var" + feed_nr + var);
/*  694 */           list.add(tripel_var);
/*  695 */           list.add("rdf:predicate");
/*  696 */           list.add("foaf:homepage");
/*  697 */           list.add(tripel_var);
/*  698 */           list.add("rdf:object");
/*  699 */           list.add("ne:" + nam);
/*      */         }
/*  701 */         else if (typ.equals("email")) {
/*  702 */           list.add(tripel_var);
/*  703 */           list.add("rdf:subject");
/*  704 */           list.add("_:var" + feed_nr + var);
/*  705 */           list.add(tripel_var);
/*  706 */           list.add("rdf:predicate");
/*  707 */           list.add("foaf:mbox");
/*  708 */           list.add(tripel_var);
/*  709 */           list.add("rdf:object");
/*  710 */           list.add("ne:" + nam);
/*      */         }
/*      */         else {
/*  713 */           list.add(tripel_var);
/*  714 */           list.add("rdf:subject");
/*  715 */           list.add("ne:" + nam);
/*  716 */           list.add(tripel_var);
/*  717 */           list.add("rdf:predicate");
/*  718 */           list.add("rdf:type");
/*  719 */           list.add(tripel_var);
/*  720 */           list.add("rdf:object");
/*  721 */           list.add("drsclass:" + typ);
/*      */         }
/*      */       }
/*      */     }
/*  725 */     else if (s.startsWith("timex")) {
/*  726 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(date|time.*)((.*=.*,)?.*='(.*)',.*='(.*)',.*='(.*)'))");
/*  727 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  728 */       while (matcher_named.find()) {
/*  729 */         String var = matcher_named.group(3);
/*  730 */         String typ = matcher_named.group(4);
/*  731 */         String year = matcher_named.group(7);
/*  732 */         year = cleanBoxerOutput(year);
/*  733 */         String month = matcher_named.group(8);
/*  734 */         month = cleanBoxerOutput(month);
/*  735 */         String day = matcher_named.group(9);
/*  736 */         day = cleanBoxerOutput(day);
/*  737 */         String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  738 */         list.add(start_var);
/*  739 */         list.add("ex:conjunct");
/*  740 */         list.add(tripel_var);
/*  741 */         list.add(tripel_var);
/*  742 */         list.add("rdf:subject");
/*  743 */         list.add("_:var" + feed_nr + var);
/*  744 */         Triples.setVariable_List("_:var" + feed_nr + var);
/*  745 */         Triples.setVariable_List(tripel_var);
/*  746 */         if (typ.contains("date")) {
/*  747 */           list.add(tripel_var);
/*  748 */           list.add("rdf:predicate");
/*  749 */           list.add("xmls:date");
/*  750 */           list.add(tripel_var);
/*  751 */           list.add("rdf:object");
/*  752 */           list.add("\"" + year + "-" + month + "-" + day + "\"");
/*      */         }
/*  754 */         if (typ.contains("time")) {
/*  755 */           list.add(tripel_var);
/*  756 */           list.add("rdf:predicate");
/*  757 */           list.add("xmls:time");
/*  758 */           list.add(tripel_var);
/*  759 */           list.add("rdf:object");
/*  760 */           list.add("\"" + year + ":" + month + ":" + day + "\"");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*  766 */     else if (s.startsWith("card")) {
/*  767 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x.*),(.*),(.*))");
/*  768 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  769 */       while (matcher_named.find()) {
/*  770 */         String var = matcher_named.group(3);
/*  771 */         String number = matcher_named.group(4);
/*  772 */         number = cleanBoxerOutput(number);
/*  773 */         String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  774 */         list.add(start_var);
/*  775 */         list.add("ex:conjunct");
/*  776 */         list.add(tripel_var);
/*  777 */         list.add(tripel_var);
/*  778 */         list.add("rdf:subject");
/*  779 */         list.add("_:var" + feed_nr + var);
/*  780 */         Triples.setVariable_List("_:var" + feed_nr + var);
/*  781 */         Triples.setVariable_List(tripel_var);
/*  782 */         list.add(tripel_var);
/*  783 */         list.add("rdf:predicate");
/*  784 */         list.add("xmls:decimal");
/*  785 */         list.add(tripel_var);
/*  786 */         list.add("rdf:object");
/*  787 */         list.add("\"" + number + "\"");
/*      */       }
/*      */ 
/*      */     }
/*  791 */     else if (s.startsWith("eq")) {
/*  792 */       Pattern pattern_named = Pattern.compile(".*=(.*)((x[0-9]*),(x[0-9]*))");
/*  793 */       Matcher matcher_named = pattern_named.matcher(condition);
/*  794 */       while (matcher_named.find()) {
/*  795 */         String var1 = matcher_named.group(3);
/*  796 */         String var2 = matcher_named.group(4);
/*  797 */         String tripel_var = Triples.getNextAvailableVariable(start_var);
/*  798 */         list.add(start_var);
/*  799 */         list.add("ex:conjunct");
/*  800 */         list.add(tripel_var);
/*  801 */         list.add(tripel_var);
/*  802 */         list.add("rdf:subject");
/*  803 */         list.add("_:var" + feed_nr + var1);
/*  804 */         Triples.setVariable_List("_:var" + feed_nr + var1);
/*  805 */         Triples.setVariable_List(tripel_var);
/*  806 */         list.add(tripel_var);
/*  807 */         list.add("rdf:predicate");
/*  808 */         list.add("owl:sameAs");
/*  809 */         list.add(tripel_var);
/*  810 */         list.add("rdf:object");
/*  811 */         list.add("_:var" + feed_nr + var2);
/*  812 */         Triples.setVariable_List("_:var" + feed_nr + var2);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  818 */       list = extractComplexConditions(condition, start_var);
/*      */     }
/*      */ 
/*  821 */     return list;
/*      */   }
/*      */ 
/*      */   public static ArrayList extractEncapsulatedDRS(String all_drs, String var1, String var2, Triples triples)
/*      */   {
/*  834 */     ArrayList encapsulated_list = new ArrayList();
/*      */ 
/*  836 */     String[] sub_drs = split_DRS(all_drs);
/*      */ 
/*  838 */     for (int itr = 0; itr <= sub_drs.length - 1; itr++) {
/*  839 */       if (sub_drs[itr].length() <= 4)
/*      */       {
/*      */         break;
/*      */       }
/*  843 */       sub_drs[itr] = sub_drs[itr].substring(4);
/*  844 */       int dr_index = find_Splitindex(sub_drs[itr], "[", "]");
/*      */ 
/*  846 */       String discourse_referents = sub_drs[itr].substring(0, dr_index);
/*  847 */       String drs_rest = sub_drs[itr].substring(dr_index, sub_drs[itr].length() - 1);
/*  848 */       if (itr == 0) {
/*  849 */         extractConditionsRecursively(drs_rest, var1, triples);
/*      */       }
/*  851 */       if (itr == 1) {
/*  852 */         extractConditionsRecursively(drs_rest, var2, triples);
/*      */       }
/*      */     }
/*  855 */     return encapsulated_list;
/*      */   }
/*      */ 
/*      */   public static ArrayList extractComplexConditions(String condition, String prefix)
/*      */   {
/*  865 */     Story story = new Story();
/*  866 */     Triples triples = new Triples();
/*  867 */     int feed_nr = Story.getStoryNr();
/*  868 */     ArrayList complex_list = new ArrayList();
/*  869 */     String s = "";
/*  870 */     String var1 = "";
/*  871 */     String var = "";
/*  872 */     int story_nr = Story.getStoryNr();
/*  873 */     if (condition.contains("=")) {
/*  874 */       String[] hi = condition.split("=");
/*  875 */       s = hi[1];
/*      */     }
/*      */     else {
/*  878 */       s = condition;
/*      */     }
/*  880 */     if (s.startsWith("or")) {
/*  881 */       if (prefix.endsWith("x")) {
/*  882 */         var = Triples.getNextAvailableVariable(prefix);
/*      */       }
/*      */       else {
/*  885 */         var = prefix;
/*      */       }
/*  887 */       complex_list.add(var);
/*  888 */       complex_list.add("rdf:type");
/*  889 */       complex_list.add("ex:disjunction");
/*  890 */       complex_list.add(var);
/*  891 */       complex_list.add("ex:disjunct");
/*  892 */       complex_list.add(var + "0");
/*  893 */       complex_list.add(var);
/*  894 */       complex_list.add("ex:disjunct");
/*  895 */       complex_list.add(var + "1");
/*  896 */       complex_list.add(var + "0");
/*  897 */       complex_list.add("rdf:type");
/*  898 */       complex_list.add("ex:conjunction");
/*  899 */       complex_list.add(var + "1");
/*  900 */       complex_list.add("rdf:type");
/*  901 */       complex_list.add("ex:conjunction");
/*      */ 
/*  903 */       String sub = "";
/*  904 */       Pattern cond_pattern = Pattern.compile("=or\\((drs.*)");
/*  905 */       Matcher cond_matcher = cond_pattern.matcher(condition);
/*  906 */       while (cond_matcher.find()) {
/*  907 */         sub = cond_matcher.group(1);
/*      */       }
/*  909 */       extractEncapsulatedDRS(sub, var + "0", var + "1", triples);
/*      */     }
/*  911 */     else if (s.startsWith("imp")) {
/*  912 */       if (prefix.endsWith("x")) {
/*  913 */         var = Triples.getNextAvailableVariable(prefix);
/*      */       }
/*      */       else {
/*  916 */         var = prefix;
/*      */       }
/*  918 */       complex_list.add(var);
/*  919 */       complex_list.add("rdf:type");
/*  920 */       complex_list.add("ex:implication");
/*  921 */       complex_list.add(var + "0");
/*  922 */       complex_list.add("ex:antecedent");
/*  923 */       complex_list.add(var);
/*  924 */       complex_list.add(var + "1");
/*  925 */       complex_list.add("ex:consequent");
/*  926 */       complex_list.add(var);
/*  927 */       complex_list.add(var + "0");
/*  928 */       complex_list.add("rdf:type");
/*  929 */       complex_list.add("ex:conjunction");
/*  930 */       complex_list.add(var + "1");
/*  931 */       complex_list.add("rdf:type");
/*  932 */       complex_list.add("ex:conjunction");
/*      */ 
/*  934 */       String sub = "";
/*  935 */       Pattern cond_pattern = Pattern.compile("=imp\\((drs.*)");
/*  936 */       Matcher cond_matcher = cond_pattern.matcher(condition);
/*  937 */       while (cond_matcher.find()) {
/*  938 */         sub = cond_matcher.group(1);
/*      */       }
/*  940 */       extractEncapsulatedDRS(sub, var + "0", var + "1", triples);
/*      */     }
/*  943 */     else if (s.startsWith("not")) {
/*  944 */       if (prefix.endsWith("x")) {
/*  945 */         var = Triples.getNextAvailableVariable(prefix);
/*      */       }
/*      */       else {
/*  948 */         var = prefix;
/*      */       }
/*  950 */       complex_list.add(var);
/*  951 */       complex_list.add("rdf:type");
/*  952 */       complex_list.add("ex:negation");
/*  953 */       complex_list.add(var);
/*  954 */       complex_list.add("rdf:type");
/*  955 */       complex_list.add("ex:conjunction");
/*      */ 
/*  957 */       String sub = "";
/*  958 */       Pattern cond_pattern = Pattern.compile("=not\\((drs.*)");
/*  959 */       Matcher cond_matcher = cond_pattern.matcher(condition);
/*  960 */       while (cond_matcher.find()) {
/*  961 */         sub = cond_matcher.group(1);
/*      */       }
/*  963 */       extractEncapsulatedDRS(sub, var, "", triples);
/*      */     }
/*  965 */     else if (s.startsWith("nec")) {
/*  966 */       if (prefix.endsWith("x")) {
/*  967 */         var = Triples.getNextAvailableVariable(prefix);
/*      */       }
/*      */       else {
/*  970 */         var = prefix;
/*      */       }
/*  972 */       complex_list.add(var);
/*  973 */       complex_list.add("rdf:type");
/*  974 */       complex_list.add("ex:necessity");
/*  975 */       complex_list.add(var);
/*  976 */       complex_list.add("rdf:type");
/*  977 */       complex_list.add("ex:conjunction");
/*      */ 
/*  979 */       String sub = "";
/*  980 */       Pattern cond_pattern = Pattern.compile("=nec\\((drs.*)");
/*  981 */       Matcher cond_matcher = cond_pattern.matcher(condition);
/*  982 */       while (cond_matcher.find()) {
/*  983 */         sub = cond_matcher.group(1);
/*      */       }
/*  985 */       extractEncapsulatedDRS(sub, var, "", triples);
/*      */     }
/*  987 */     else if (s.startsWith("pos")) {
/*  988 */       if (prefix.endsWith("x")) {
/*  989 */         var = Triples.getNextAvailableVariable(prefix);
/*      */       }
/*      */       else {
/*  992 */         var = prefix;
/*      */       }
/*  994 */       complex_list.add(var);
/*  995 */       complex_list.add("rdf:type");
/*  996 */       complex_list.add("ex:possibility");
/*  997 */       complex_list.add(var);
/*  998 */       complex_list.add("rdf:type");
/*  999 */       complex_list.add("ex:conjunction");
/*      */ 
/* 1001 */       String sub = "";
/* 1002 */       Pattern cond_pattern = Pattern.compile("=pos\\((drs.*)");
/* 1003 */       Matcher cond_matcher = cond_pattern.matcher(condition);
/* 1004 */       while (cond_matcher.find()) {
/* 1005 */         sub = cond_matcher.group(1);
/*      */       }
/* 1007 */       extractEncapsulatedDRS(sub, var, "", triples);
/*      */     }
/* 1009 */     else if (s.startsWith("whq")) {
/* 1010 */       Pattern pattern_whq = Pattern.compile(".*=whq\\(\\[(.*)\\],drs(.*),(x[0-9]*),drs\\(.*\\)");
/* 1011 */       Matcher matcher_whq = pattern_whq.matcher(condition);
/* 1012 */       while (matcher_whq.find()) {
/* 1013 */         var = matcher_whq.group(3);
/* 1014 */         String answertype = matcher_whq.group(2);
/* 1015 */         complex_list.add("_:var" + var);
/* 1016 */         complex_list.add("rdf:type");
/* 1017 */         complex_list.add("ex:referent");
/* 1018 */         complex_list.add("_:var" + var + "0");
/* 1019 */         complex_list.add("ex:answer");
/* 1020 */         complex_list.add("_:var" + var);
/* 1021 */         complex_list.add("_:var" + var + "1");
/* 1022 */         complex_list.add("ex:question");
/* 1023 */         complex_list.add("_:var" + var);
/* 1024 */         complex_list.add("_:var" + var + "0");
/* 1025 */         complex_list.add("rdf:type");
/* 1026 */         complex_list.add("ex:conjunction");
/* 1027 */         complex_list.add("_:var" + var + "1");
/* 1028 */         complex_list.add("rdf:type");
/* 1029 */         complex_list.add("ex:conjunction");
/*      */ 
/* 1031 */         String sub = "";
/* 1032 */         Pattern cond_pattern = Pattern.compile("=whq\\(\\[(.*)\\],(drs\\(.*)");
/* 1033 */         Matcher cond_matcher = cond_pattern.matcher(condition);
/* 1034 */         while (cond_matcher.find()) {
/* 1035 */           sub = cond_matcher.group(2);
/*      */         }
/* 1037 */         extractEncapsulatedDRS(sub, "_:var" + var + "0", "_:var" + var + "1", triples);
/*      */       }
/*      */     }
/* 1040 */     else if (s.startsWith("prop")) {
/* 1041 */       Pattern pattern_named = Pattern.compile(".*=prop\\((x[0-9]*)");
/* 1042 */       Matcher matcher_named = pattern_named.matcher(condition);
/* 1043 */       while (matcher_named.find()) {
/* 1044 */         var1 = matcher_named.group(1);
/* 1045 */         complex_list.add("_:var" + feed_nr + var1);
/* 1046 */         complex_list.add("rdf:type");
/* 1047 */         complex_list.add("ex:proposition");
/* 1048 */         complex_list.add("_:var" + feed_nr + var1);
/* 1049 */         complex_list.add("rdf:type");
/* 1050 */         complex_list.add("ex:conjunction");
/*      */ 
/* 1052 */         String sub = "";
/* 1053 */         Pattern cond_pattern = Pattern.compile("=prop\\(x[0-9]*,(drs\\(.*)");
/* 1054 */         Matcher cond_matcher = cond_pattern.matcher(condition);
/* 1055 */         while (cond_matcher.find()) {
/* 1056 */           sub = cond_matcher.group(1);
/*      */         }
/* 1058 */         extractEncapsulatedDRS(sub, "_:var" + feed_nr + var1, "", triples);
/*      */       }
/*      */     }
/*      */ 
/* 1062 */     return complex_list;
/*      */   }
/*      */ 
/*      */   public static String extractConditionsRecursively(String all_conditions_rest, Triples triples)
/*      */   {
/* 1073 */     if ((all_conditions_rest.length() <= 10) || (!all_conditions_rest.contains("="))) {
/* 1074 */       return "";
/*      */     }
/*      */ 
/* 1078 */     int endIndex = find_Splitindex(all_conditions_rest, "(", ")");
/*      */ 
/* 1080 */     String all_conditions_part = all_conditions_rest.substring(0, endIndex);
/* 1081 */     if (all_conditions_part.startsWith(",")) {
/* 1082 */       all_conditions_part = all_conditions_part.substring(2);
/*      */     }
/*      */ 
/* 1085 */     all_conditions_rest = all_conditions_rest.substring(endIndex);
/*      */ 
/* 1087 */     ArrayList list = extractConditions(all_conditions_part);
/* 1088 */     int cnt = 0;
/* 1089 */     for (Iterator it = list.iterator(); it.hasNext(); ) {
/* 1090 */       String str = (String)it.next();
/* 1091 */       if (cnt <= 2)
/*      */       {
/* 1093 */         Triples.setRdfList(str + " ");
/* 1094 */         cnt++;
/*      */       }
/*      */       else
/*      */       {
/* 1098 */         Triples.setRdfList(".\n" + str + " ");
/* 1099 */         cnt = 1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1104 */     if (!Triples.getRdf().endsWith(".\n"))
/*      */     {
/* 1108 */       Triples.setRdfList(".\n");
/*      */     }
/*      */ 
/* 1111 */     return extractConditionsRecursively(all_conditions_rest, triples);
/*      */   }
/*      */ 
/*      */   public static String extractConditionsRecursively(String all_conditions_rest, String start_var, Triples triples)
/*      */   {
/* 1123 */     if ((all_conditions_rest.length() <= 10) || (!all_conditions_rest.contains("="))) {
/* 1124 */       return "";
/*      */     }
/*      */ 
/* 1128 */     int endIndex = find_Splitindex(all_conditions_rest, "(", ")");
/*      */ 
/* 1130 */     String all_conditions_part = all_conditions_rest.substring(0, endIndex);
/* 1131 */     if (all_conditions_part.startsWith(", ")) {
/* 1132 */       all_conditions_part = all_conditions_part.substring(2);
/*      */     }
/*      */ 
/* 1135 */     all_conditions_rest = all_conditions_rest.substring(endIndex);
/*      */ 
/* 1137 */     ArrayList list = extractConditions(all_conditions_part, start_var);
/* 1138 */     int cnt = 0;
/* 1139 */     for (Iterator it = list.iterator(); it.hasNext(); ) {
/* 1140 */       String str = (String)it.next();
/* 1141 */       if (cnt <= 2)
/*      */       {
/* 1143 */         Triples.setRdfList(str + " ");
/* 1144 */         cnt++;
/*      */       }
/*      */       else
/*      */       {
/* 1148 */         Triples.setRdfList(".\n" + str + " ");
/* 1149 */         cnt = 1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1154 */     Triples.setRdfList(".\n");
/*      */ 
/* 1157 */     return extractConditionsRecursively(all_conditions_rest, start_var, triples);
/*      */   }
/*      */ 
/*      */   public static String[] extractStories(String boxer_output)
/*      */   {
/* 1167 */     String[] feed_drs = boxer_output.split("%%%");
/* 1168 */     return feed_drs;
/*      */   }
/*      */ 
/*      */   public static String writeURIs(Story[] stories, String rdf)
/*      */   {
/* 1179 */     for (int i = 0; i <= stories.length - 1; i++) {
/* 1180 */       if (stories[i] != null) {
/* 1181 */         String var = "";
/* 1182 */         String ne = "";
/* 1183 */         String feednr = "";
/* 1184 */         HashMap entity = stories[i].getEntitiy();
/* 1185 */         Iterator it = entity.keySet().iterator();
/* 1186 */         HashMap uri_var = new HashMap();
/* 1187 */         uri_var.put("test", "test");
/*      */         Matcher ne_matcher;
/* 1188 */         for (; it.hasNext(); 
/* 1200 */           ne_matcher.find())
/*      */         {
/* 1189 */           String key = (String)it.next();
/*      */ 
/* 1191 */           String key2 = key.replace(" ", "_");
/* 1192 */           key2 = key2.toLowerCase();
/*      */ 
/* 1194 */           String val = (String)entity.get(key);
/*      */ 
/* 1196 */           addURIs(val);
/* 1197 */           Pattern ne_pattern = Pattern.compile("(_:var" + i + "x.*) .*:.* .*:" + key2 + " \\.");
/*      */ 
/* 1199 */           ne_matcher = ne_pattern.matcher(rdf);
/* 1200 */           continue;
/* 1201 */           var = ne_matcher.group(1);
/*      */ 
/* 1203 */           Integer idx = Integer.valueOf(rdf.indexOf(ne_matcher.group(0)) + ne_matcher.group(0).length() + 1);
/*      */ 
/* 1207 */           if (uri_var.containsKey(val)) {
/* 1208 */             rdf = rdf.replace(var, uri_var.get(val).toString());
/*      */           }
/*      */           else {
/* 1211 */             rdf = rdf + var + " owl:sameAs " + val + " .\n";
/*      */           }
/* 1213 */           uri_var.put(val, var);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1219 */     return rdf;
/*      */   }
/*      */ 
/*      */   public static String writeURIs2(Story[] stories, String rdf)
/*      */   {
/* 1229 */     for (int i = 0; i <= stories.length - 1; i++) {
/* 1230 */       if (stories[i] != null) {
/* 1231 */         String var = "";
/* 1232 */         String ne = "";
/* 1233 */         String feednr = "";
/* 1234 */         HashMap entity = stories[i].getEntitiy();
/* 1235 */         Iterator it = entity.keySet().iterator();
/* 1236 */         HashMap uri_var = new HashMap();
/* 1237 */         uri_var.put("test", "test");
/*      */         Matcher ne_matcher;
/* 1238 */         for (; it.hasNext(); 
/* 1246 */           ne_matcher.find())
/*      */         {
/* 1239 */           String key = (String)it.next();
/* 1240 */           String key2 = key.replace(" ", "_");
/* 1241 */           key2 = key2.toLowerCase();
/* 1242 */           String val = (String)entity.get(key);
/* 1243 */           addURIs(val);
/* 1244 */           Pattern ne_pattern = Pattern.compile("(_:var" + i + "x.*) .*:.* (.*:" + key2 + ") \\.");
/* 1245 */           ne_matcher = ne_pattern.matcher(rdf);
/* 1246 */           continue;
/* 1247 */           Integer idx = Integer.valueOf(rdf.indexOf(ne_matcher.group(0)) + ne_matcher.group(0).length() + 1);
/*      */ 
/* 1251 */           var = ne_matcher.group(1);
/* 1252 */           if (uri_var.containsKey(val)) {
/* 1253 */             rdf = rdf.replace(var, uri_var.get(val).toString());
/*      */           }
/*      */           else {
/* 1256 */             rdf = rdf + var + " owl:sameAs " + val + " .\n";
/*      */           }
/* 1258 */           uri_var.put(val, var);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1267 */     return rdf;
/*      */   }
/*      */ 
/*      */   public static void makeTriples(String boxer_output)
/*      */   {
/* 1275 */     Triples triples = new Triples();
/* 1276 */     Triples.setRdf("");
/* 1277 */     Story story = new Story();
/* 1278 */     String[] feed_drs = extractStories(boxer_output);
/* 1279 */     for (int i = 3; i <= feed_drs.length - 1; i++) {
/* 1280 */       Story.setStoryNr(i - 3);
/* 1281 */       String all_drs = extractDRSStructure(feed_drs[i]);
/* 1282 */       int idx = find_Splitindex(all_drs, "(", ")");
/* 1283 */       if (all_drs.startsWith("(")) {
/* 1284 */         all_drs = all_drs.substring(1, idx - 1);
/*      */       }
/*      */ 
/* 1288 */       ArrayList temp = new ArrayList();
/* 1289 */       String[] sub_drs = split_DRS_recursively(all_drs, temp);
/*      */ 
/* 1291 */       for (int itr = 0; itr <= sub_drs.length - 1; itr++)
/*      */       {
/* 1293 */         if (sub_drs[itr].length() <= 4)
/*      */         {
/*      */           break;
/*      */         }
/* 1297 */         sub_drs[itr] = sub_drs[itr].substring(4);
/* 1298 */         int dr_index = find_Splitindex(sub_drs[itr], "[", "]");
/*      */ 
/* 1300 */         String discourse_referents = sub_drs[itr].substring(0, dr_index);
/*      */ 
/* 1302 */         String drs_rest = sub_drs[itr].substring(dr_index + 2, sub_drs[itr].length() - 1);
/*      */ 
/* 1304 */         extractConditionsRecursively(drs_rest, triples);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void makeTriples2(String boxer_output)
/*      */   {
/* 1313 */     Triples triples = new Triples();
/* 1314 */     Triples.setRdf("");
/* 1315 */     Story story = new Story();
/* 1316 */     Story.setStoryNr(0);
/* 1317 */     String all_drs = extractDRSStructure(boxer_output);
/* 1318 */     int idx = find_Splitindex(all_drs, "(", ")");
/* 1319 */     if (all_drs.startsWith("(")) {
/* 1320 */       all_drs = all_drs.substring(1, idx - 1);
/*      */     }
/*      */ 
/* 1324 */     ArrayList temp = new ArrayList();
/* 1325 */     String[] sub_drs = split_DRS_recursively(all_drs, temp);
/*      */ 
/* 1327 */     for (int itr = 0; itr <= sub_drs.length - 1; itr++)
/*      */     {
/* 1329 */       if (sub_drs[itr].length() <= 4)
/*      */       {
/*      */         break;
/*      */       }
/* 1333 */       sub_drs[itr] = sub_drs[itr].substring(4);
/* 1334 */       int dr_index = find_Splitindex(sub_drs[itr], "[", "]");
/*      */ 
/* 1336 */       String discourse_referents = sub_drs[itr].substring(0, dr_index);
/*      */ 
/* 1338 */       String drs_rest = sub_drs[itr].substring(dr_index + 2, sub_drs[itr].length() - 1);
/*      */ 
/* 1340 */       extractConditionsRecursively(drs_rest, triples);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     rdf.RelationshipExtraction
 * JD-Core Version:    0.6.2
 */