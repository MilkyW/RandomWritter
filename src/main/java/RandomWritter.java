import java.util.Scanner;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Queue;
import java.io.File;

public class RandomWritter {
    private static final String flag_1 = "-enable complete sentence";
    private static final String flag_0 = "-disable complete sentence";

    private static int validNum(String num, int[] n) {
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) > '9' || num.charAt(i) < '0') return 1;
        }
        n[0] = Integer.valueOf(num);
        if (n[0] < 2) return 2;
        return 0;
    }

    private static int validLen(String num, int[] n) {
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) > '9' || num.charAt(i) < '0') return 1;
        }
        n[0] = Integer.valueOf(num);
        if (n[0] == 0) return 3;
        if (n[0] < 4) return 2;
        return 0;
    }

    public static void main(String[] args) {
        int mode = 0;
        System.out.print("input file name? ");
        Scanner input = new Scanner(System.in);
        String filename = input.nextLine();
        String fileaddr = "src/main/text/" + filename;
        File file = new File(fileaddr);
        //System.out.println(file.getAbsolutePath());

        while (!file.exists()) {
            switch (filename) {
                case flag_1: {
                    if (mode == 0) {
                        mode = 1;
                        System.out.println("Complete sentence mode enabled.");
                    } else {
                        System.out.println("Complete sentence already enabled.");
                    }
                }
                case flag_0: {
                    if (mode == 1) {
                        mode = 0;
                        System.out.println("Complete sentence mode disabled.");
                    } else {
                        System.out.println("Complete sentence already disabled.");
                    }
                }
                default: {
                    System.out.println("Unable to open that file.  Try again.");
                }
            }
            System.out.println("input file name? ");
            filename = input.nextLine();
            fileaddr = "src/main/text/" + filename;
            file = new File(fileaddr);
        }

        try {
            Scanner ifile = new Scanner(file);

            String num;
            int[] n = new int[1];
            System.out.print("Value of N? ");
            num = input.nextLine();

            while (validNum(num, n) != 0) {
                switch (validNum(num, n)) {
                    case 1:
                        System.out.println("Illegal integer format. Try again.");
                        break;
                    case 2:
                        System.out.println("N must be 2 or greater.");
                        break;
                }
                System.out.print("Value of N? ");
                num = input.nextLine();
            }


            //int fileLen = 0;
            Hashtable<String, Hashtable<String, Integer>> grams =
                    new Hashtable<String, Hashtable<String, Integer>>();
            Hashtable<String, Integer> hits = new Hashtable<String, Integer>();
            Queue<Integer> length = new LinkedList<Integer>();
            String temp = "";
            String buffer;
            long t1 = System.currentTimeMillis();
            buffer = ifile.next();
            for (int i = 0; i < n[0]; i++) {
                temp = temp.concat(buffer).concat(" ");
                length.offer(buffer.length() + 1);
                buffer = ifile.next();
            }

            //fileLen += n;
            Integer hitTimes;
            Hashtable<String, Integer> hittie;
            while (ifile.hasNext()) {
                //fileLen++;
                hitTimes = hits.get(temp);
                if (hitTimes == null) {
                    hits.put(temp, 1);
                } else hits.put(temp, hitTimes + 1);

                hittie = grams.get(temp);
                if (hittie == null) {
                    hittie = new Hashtable<String, Integer>();
                    hittie.put(buffer, 1);
                    grams.put(temp, hittie);
                } else {
                    hitTimes = hittie.get(buffer);
                    if (hitTimes == null) {
                        hittie.put(buffer, 1);
                    } else hittie.put(buffer, hitTimes + 1);
                    grams.put(temp, hittie);
                }

                temp = temp.substring(length.peek());
                length.poll();
                temp = temp.concat(buffer).concat(" ");
                length.offer(buffer.length() + 1);
                buffer = ifile.next();
            }

            //fileLen++;
            hitTimes = hits.get(temp);
            if (hitTimes == null) {
                hits.put(temp, 0);
            }
            long t2 = System.currentTimeMillis();
            System.out.print("Time consumed: ");
            System.out.print(t2 - t1);
            System.out.println(" ms");

            int[] len = new int[1];
            System.out.println();
            System.out.print("# of random words to generate (0 to quit)? ");

            while (input.hasNext()) {
                num = input.nextLine();
                switch (validLen(num, len)) {
                    case 1:
                        System.out.println("Illegal integer format. Try again.");
                        break;
                    case 2:
                        System.out.println("Must be at least 4 words.");
                        break;
                    case 3:
                        System.out.println("Exiting.");
                        System.exit(0);
                    case 0:
                        t1 = System.currentTimeMillis();
                        String out = "";
                        int sum = hits.size();
                        int random;
                        if (sum == 0) break; // n >= file length;
                        //if (len > fileLen) break; //len > file length;
                        Iterator<Entry<String, Integer>> ite1 = null;
                        Iterator<Entry<String, Integer>> ite2 = null;
                        Entry<String, Integer> e1 = null;
                        Entry<String, Integer> e2 = null;
                        if (mode == 0) {
                            int hit = 0;
                            while (hit == 0) {
                                //System.out.println(System.currentTimeMillis());
                                random = (int) Math.abs(System.currentTimeMillis() % sum);
                                ite2 = hits.entrySet().iterator();
                                int i = 0;
                                do {
                                    e2 = ite2.next();
                                    i += e2.getValue();
                                } while (i < random);
                                hit = e2.getValue();
                            }
                            temp = e2.getKey();
                            out = out.concat("... ").concat(temp);
                        } else {
                            temp = " ";
                            while (temp.charAt(0) < 'A' || temp.charAt(0) > 'Z') {
                                System.out.println(System.currentTimeMillis());
                                random = (int) Math.abs(System.currentTimeMillis() % sum);
                                ite2 = hits.entrySet().iterator();
                                int i = 0;
                                do {
                                    e2 = ite2.next();
                                    i += e2.getValue();
                                } while (i < random);
                                temp = e2.getKey();
                            }
                            out += temp;
                        }
                        length.clear();
                        String[] s = temp.split(" ");
                        String test = "";
                        for (int i = 0; i < n[0]; i++) {
                            buffer = s[i];
                            length.offer(buffer.length() + 1);
                        }
                        boolean end = false;
                        for (int i = n[0]; i < len[0]; i++) {
                            if (hits.get(temp) > 0) {
                                random = (int) Math.abs(System.currentTimeMillis() % hits.get(temp));
                                ite1 = grams.get(temp).entrySet().iterator();
                                int j = 0;
                                do {
                                    e1 = ite1.next();
                                    j += e1.getValue();
                                } while (j < random);
                                buffer = e1.getKey();
                                temp = temp.substring(length.peek());
                                temp += buffer;
                                temp += " ";
                                out += buffer;
                                out += " ";
                                length.poll();
                                length.offer(buffer.length() + 1);
                            } else {
                                end = true;
                                break;
                            }
                        }
                        if (mode > 0) {
                            while (buffer.charAt(buffer.length() - 1) != '.' &&
                                    buffer.charAt(buffer.length() - 1) != '?'
                                    && buffer.charAt(buffer.length() - 1) != '!') {
                                random = (int) Math.abs(System.currentTimeMillis() % hits.get(temp));
                                ite1 = grams.get(temp).entrySet().iterator();
                                int j = 0;
                                do {
                                    e1 = ite1.next();
                                    j += e1.getValue();
                                } while (j < random);
                                buffer = e1.getKey();
                                temp = temp.substring(length.peek());
                                temp = temp.concat(buffer).concat(" ");
                                out = out.concat(buffer).concat(" ");
                                length.poll();
                                length.offer(buffer.length() + 1);
                            }
                        }
                        if (mode == 0 && !end) {
                            out = out.concat("...");
                        }
                        System.out.println(out);
                        t2 = System.currentTimeMillis();
                        System.out.print("Time consumed: ");
                        System.out.print(t2 - t1);
                        System.out.println(" ms");
                        break;
                }
                 System.out.println();
                System.out.print("# of random words to generate (0 to quit)? ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
