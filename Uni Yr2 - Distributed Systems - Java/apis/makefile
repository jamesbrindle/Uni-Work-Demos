SRC=		mcastd
BINDIR=		$(HOME)/bin
EXE=		$(SRC)

$(EXE):		$(SRC).c
		gcc -o $(EXE) $(SRC).c -lnsl 
		chmod 755 $(EXE)

