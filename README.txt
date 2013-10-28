###############################################################################
#                                                                             #
# README for the LODifier package.		                              #
#                                                                             #
###############################################################################

# Installation
For LODifier to work, you will need a working copy of C&C and Boxer.
For further instructions see
http://svn.ask.it.usyd.edu.au/trac/candc/wiki/Installation
Please use the development version 1752 from the subversion repository.
Other versions might work as well, but have not been tested.

You will also need a working copy of UKB, see http://ixa2.si.ehu.es/ukb/

Adjust the properties in LODifier/src/conf/LODifier.properties according to
your system configuration.

# Run
To execute LODifier, run 
$ java -jar LODifier.jar <input file> <output file>
e.g. '$ java -jar LODifier.jar input.txt output.txt'
