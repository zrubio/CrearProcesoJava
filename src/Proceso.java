public class Proceso {

    public static void main(String[] args) {

        // Crear jar y luego ejecutar el jar con argumentos: notepad.exe
        // $ javar -jar CrearProcesoJava.jar notepad.exe

        // Leer argumentos
        if (args.length <= 0){
            System.out.println("No arguments");
            System.exit(-1);
        }

        // Construcción del proceso
        System.out.println("Construyendo el proceso");
        ProcessBuilder processBuilder = new ProcessBuilder(args[0]);

        try{
            // Inicia el proceso
            System.out.println("Iniciar proceso");
            Process process = processBuilder.start();
            // Devuelve si el subproceso ha terminado correctamente
            int subproceso = process.waitFor();
            System.out.println("El proceso hijo ha finalizado correctamente");
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("El proceso hijo ha finalizado incorrectamente");
            System.exit(-1);
        }

    }
}
