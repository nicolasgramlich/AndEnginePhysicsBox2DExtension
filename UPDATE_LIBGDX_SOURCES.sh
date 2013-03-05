set -e

echo "\nDownloading box2d from libgdx..."
wget -r -k --tries 10 --reject=html -np -P jni/Box2D http://libgdx.googlecode.com/svn/trunk/gdx/jni/Box2D/
echo "\nCopying files..."
cp jni/Box2D/libgdx.googlecode.com/svn/trunk/gdx/jni/Box2D/**.* jni/Box2D
echo "Removing junk..."
rm -r jni/Box2D/libgdx.googlecode.com

echo "\nDownloading box2d physics from libgdx..."
wget -r -k --tries 10 --reject=html -np -P src http://libgdx.googlecode.com/svn/trunk/gdx/src/com/badlogic/gdx/physics/box2d/
echo "\nCopying files..."
cp src/libgdx.googlecode.com/svn/trunk/gdx/src/com/badlogic/gdx/physics/**.* src/com/badlogic/gdx/physics
echo "\nRemoving junk..."
rm -r src/libgdx.googlecode.com/
