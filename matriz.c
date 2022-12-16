#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {

  int world_size;
  int rank;
  int rows, offset;
  int matrix_size;
  char *p;
  int err = 0;
  
  long conversion = strtol(argv[1], &p, 10);


  if (err != 0 || *p != '\0') {
    printf("Deve ser passado um inteiro como tamanho da matriz");
  } else {
	matrix_size = conversion;
  }

  int A[matrix_size][matrix_size];
  int B[matrix_size][matrix_size];
  int C[matrix_size][matrix_size];

  MPI_Init(NULL, NULL);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &world_size);

  if(world_size % 2 != 0) {
    MPI_Abort(MPI_COMM_WORLD, 1);
  }

  if (rank == 0){

    rows = matrix_size / (world_size - 1);
    offset = 0;

    for (int i=0; i<matrix_size; i++){
      for (int j=0; j<matrix_size; j++){
        A[i][j] = rand() % 1000;
        B[i][j] = rand() % 1000;
      }
    }

    for (int count=0; count<world_size; count++){
      if (count == 0){
        for (int i=0; i<rows; i++) {
          for (int j=0; j<matrix_size; j++){
            C[i][j] = A[i][j] + B[i][j];
          }
        }

      } else {
        MPI_Send(&offset, 1, MPI_INT, count, 1, MPI_COMM_WORLD);
        MPI_Send(&rows, 1, MPI_INT, count, 1, MPI_COMM_WORLD);
        MPI_Send(&A[offset][0], rows*matrix_size, MPI_INT,count,1, MPI_COMM_WORLD);
        MPI_Send(&B, matrix_size*matrix_size, MPI_INT, count, 1, MPI_COMM_WORLD);
      }
      offset = offset + rows;
    }

    for (int i=1; i<world_size; i++){
      int source = i;
      MPI_Recv(&offset, 1, MPI_INT, source, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      MPI_Recv(&rows, 1, MPI_INT, source, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      MPI_Recv(&C[offset][0], rows*matrix_size, MPI_INT, source, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }

    printf("A matriz C é:\n");
    for (int i=0; i<matrix_size; i++) {
      for (int j=0; j<matrix_size; j++)
        printf("%d   ", C[i][j]);
      printf ("\n");
    }

  } else if (rank > 0) {
    MPI_Recv(&offset, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(&rows, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(&A, rows*matrix_size, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(&B, matrix_size*matrix_size, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

    for (int i=0; i<rows; i++) {
      for (int j=0; j<matrix_size; j++){
        C[i][j] = A[i][j] + B[i][j];
      }
    }


    MPI_Send(&offset, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
    MPI_Send(&rows, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
    MPI_Send(&C, rows*matrix_size, MPI_INT, 0, 2, MPI_COMM_WORLD);
  }

  MPI_Finalize();
}
