cd $JAVA_HOME
rm -rf ./*
curl -LO https://corretto.aws/downloads/latest/amazon-corretto-16-x64-linux-jdk.tar.gz
tar -xf amazon-corretto-16-x64-linux-jdk.tar.gz
mv amazon-corretto-16.0.1.12.1-linux-x64/* ./
rm -rf temp/
echo "Process finished"
