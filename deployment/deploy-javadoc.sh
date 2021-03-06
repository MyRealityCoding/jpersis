#!/bin/bash

echo "Deploying Javadoc to Github pages"

cd $HOME
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet --branch=master https://${GITHUB_TOKEN}@github.com/bitbrain/jpersis
mkdir docs
cd jpersis
mvn javadoc:javadoc
cd ..
mv -f jpersis/target/site/apidocs/* docs
cd jpersis
git checkout gh-pages
mv -f ../docs .
rm -rf target
git add -f .
git commit -m "Travis build $TRAVIS_BUILD_NUMBER - update Javadocs"
git push -fq origin gh-pages > /dev/null
echo "Successfully deployed Javadoc to /docs"
