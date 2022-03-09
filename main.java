import java.io.*;
import java.util.*;

class Output{
    String msg;
    Output(String msg){
        this.msg = msg;
    }
    public String showMsg(){
        return this.msg;
    }
}

class Company {

    boolean turn = true;

    int[] task,ex;

    public void ms(int[] tasks,int[] exe,int lo,int hi){
        if(lo==hi) return;
        int mid = (lo+hi)/2;
        ms(tasks, exe, lo, mid);
        ms(tasks, exe, mid+1, hi);
        merge(tasks, exe, lo, hi);
    }

    public void merge(int[] tasks,int[] exe,int lo,int hi){
        int mid = (lo+hi)/2;
        int[] temp = new int[hi-lo+1];
        int[] temp2 = new int[hi-lo+1];
        int p1=lo,p2=mid+1,k=0;
        while(p1<=mid&&p2<=hi){
            if(tasks[p1]<=tasks[p2]){
                temp[k]=tasks[p1];
                temp2[k]=exe[p1];
                k++;
                p1++;
            }else{
                temp[k]=tasks[p2];
                temp2[k]=exe[p2];
                k++;
                p2++;
            }
        }
        while(p1<=mid){
            temp[k]=tasks[p1];
            temp2[k]=exe[p1];
            k++;
            p1++;
        }
        while(p2<=hi){
            temp[k]=tasks[p2];
            temp2[k]=exe[p2];
            k++;
            p2++;
        }
        for(int i=0;i<temp.length;i++){
            tasks[lo+i]=temp[i];
            exe[lo+i]=temp2[i];
        }
    }

    Company(){
        task = new int[10];
        ex = new int[10];
        for(int i=0;i<10;i++){
            task[i] = Integer.MAX_VALUE;
            ex[i] = -1;
        }
    }

    public int generateId(){
        Random rn = new Random();
        return rn.nextInt(Integer.MAX_VALUE);
    }

    public String giveEx(){
        int ind = 0;
        System.out.println(ind);
        //searching for ind whose tasks are < 10
        while(ex[ind]!=-1&&task[ind]>=10&&task[ind]!=Integer.MAX_VALUE){
            System.out.println("hi");
            ind++;
        }
        if(ind<0){
            ind=0;
            while(task[ind]!=Integer.MAX_VALUE) ind++;
            if(ind>=task.length){
                return new Output("Can't hire executives as all have max tasks "+ind).showMsg();
            }
        }
        System.out.println("giveEx ind "+ind);
        task[ind]++;
        return new Output("Executive "+ ex[ind]+" is assigned task ").showMsg();
    }

    public String addTask(){
        String ret = "";
        System.out.println(turn);
        if(turn){
            turn=false;
            int ind = 0;
            while(ind<10&&task[ind]!=Integer.MAX_VALUE){
                ind++;
            }
            if(ind>=task.length){
                return giveEx();
            }
            task[ind]=1;
            ex[ind] = generateId();
            ret = "Executive "+ex[ind]+" is hired\nExecutive "+ex[ind]+" was assigned a task";
        }else{
            turn=true;
            ret = giveEx();
        }
        ms(task,ex,0, task.length-1);
        return ret;
    }

    public String completeTask(int id){
        boolean flag = false;
        int ind = 0;
        while(ind< ex.length){
            if(ex[ind]==id){
                flag=true;
                break;
            }
            ind++;
        }
        if(!flag) return new Output("No such id exists").showMsg();
        task[ind]--;
        int tasksOfEx = task[ind];
        if(tasksOfEx<=0) {
            int count=0,i=0;
            while(i<10){
                if(ex[i]!=-1)count++;
                i++;
            }
            if(count==2) {
                return new Output("Company must have atleast 2 executives so cant remove anyone").showMsg();
            }
            task[ind] = Integer.MAX_VALUE;
            ex[ind] = -1;
            ms(task,ex,0, task.length-1);
            return new Output("Executive " + id + " released").showMsg();
        }
        ms(task,ex,0, task.length-1);
        return new Output("Executive " + id + " completed a task").showMsg();
    }

}

public class Assignment_4 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Company company = new Company();
        company.ex[0]=company.generateId();
        company.task[0] = 0;
        company.ex[1]=company.generateId();
        company.task[1] = 0;
        while (true){
            System.out.println("Enter 1 to add task \nEnter 2 to complete task\nEnter 3 to stop\nEnter your choice : ");
            String s = br.readLine();
            if(s.equals("1")){
                System.out.println(company.addTask());
            }else if(s.equals("2")){
                System.out.println("Enter executive id : ");
                int id = Integer.parseInt(br.readLine());
                System.out.println(company.completeTask(id));
            }else {
                System.out.println(("stopped"));
                return;
            }
            for(int i=0;i<company.ex.length;i++){
                if(company.ex[i]!=-1){
                    System.out.println("Executive "+company.ex[i]+" has "+ company.task[i]+" tasks "+i);
                }
            }
        }
    }
}
