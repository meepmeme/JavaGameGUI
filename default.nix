{ pkgs ? import <nixpkgs> {} }:
pkgs.stdenv.mkDerivation {
  buildInputs = [ pkgs.openjdk8 ];
  name = "JMinesweeper";
  src = fetchgit {
    url = "https://github.com/meepmeme/JavaGameGUI.git";
  };
  deps = [ pkgs.openjdk8 ];
  buildPhase = ''
make clean jar
echo "#!/usr/bin/env java -jar" > ./run.sh
chmod +x ./run.sh
cat ./JMinesweeper.jar >> ./run.sh
'';
  installPhase = ''
mkdir --parents "$out/bin"
cp ./run.sh "$out/bin"/JMinesweeper
'';
}
