# Download libs
wget http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar -O hamcrest.jar
wget http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar -O junit.jar

# Compile sources and runt tests
mkdir -p bin
find . -type f -name "*.java" > sources.txt
javac -d ./bin/ -cp junit.jar hamcrest.jar @sources.txt
java -cp junit.jar:hamcrest.jar:./bin/ org.junit.runner.JUnitCore AllTests

# Clean up
rm hamcrest.jar
rm junit.jar
rm sources.txt
