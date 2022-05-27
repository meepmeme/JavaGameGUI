{ pkgs ? import <nixpkgs> {} }:
pkgs.stdenv.mkDerivation {
  buildInputs = [ pkgs.openjdk8 ];
  name = "JMinesweeper";
  version = "0.0.1";
  src = ./.;
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
