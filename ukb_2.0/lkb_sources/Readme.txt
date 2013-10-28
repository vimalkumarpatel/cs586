Files under this catalogue:

17/
   wnet17_dict.txt	Dictionary for WordNet 1.7
   wnet17_rels.txt	WordNet 1.7 relations
   xwnet17_rels.txt	eXtended WordNet 1.7 relations

30/
   wnet30_dict.txt      Dictionary for WordNet 3.0
   wnet30_rels.txt	WordNet 3.0 relations
   wnet30g_rels.txt	WordNet 3.0 gloss relations

   Note: for creating a graph comprising Wordnet3.0 + gloss relations
   	 specifying both relation files to 'compile_kb', i.e.:

	 % ./compile_kb -o wn30+gloss.bin wnet30_rels.txt wnet30g_rels.txt

These datafiles have been derived from the publicly available information in:

http://wordnet.princeton.edu/ (WordNet versions and gloss relations)
http://xwn.hlt.utdallas.edu/  (eXtended WordNet relations)


