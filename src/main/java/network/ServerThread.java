//package network;
//
//import sample.StageController;
//
//import java.net.ServerSocket;
//
//public class ServerThread extends Thread
//{
//    StageController stageController = StageController.getInstance();
//
//    @Override
//    public void run()
//    {
//        try
//        {
//            ServerSocket serverSocket = new ServerSocket(stageController.port);
//
//            System.out.println("Waiting for file choosing");
//
//            while (true)
//            {
//                Thread.sleep(5000);
//
//                if(stageController.isConnected)
//                {
//                    break;
//                }
//            }
//
//            System.out.println("File choosed");
//
//            Thread clientThread;
//
//            while (true)
//            {
//                System.out.println("Waiting for client");
//                clientThread = new Thread(new ClientThread(serverSocket.accept()));
//                clientThread.setDaemon(true);
//                clientThread.start();
//                stageController.numberOfConnections++;
//            }
//        }
//        catch (Exception ex)
//        {
//            System.out.println(ex);
//        }
//    }
//}
