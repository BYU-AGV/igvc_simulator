package com.byu_igvc.logger;

public class ProgressBar {
    private int maxLength;
    private double percent;

    private String PROGRESS_START = "\u252B";
    private String PROGRESS_END = "\u2523";

    private String INCREMENT_1 = "\u258F";
    private String INCREMENT_2 = "\u258E";
    private String INCREMENT_3 = "\u258D";
    private String INCREMENT_4 = "\u258C";
    private String INCREMENT_5 = "\u258B";
    private String INCREMENT_6 = "\u258A";
    private String INCREMENT_7 = "\u2589";
    private String INCREMENT_8 = "\u2588";

    public ProgressBar(int width) {
        this.maxLength = width;
    }

    public void startProgressBar() {
        System.out.print(PROGRESS_START + "          " + PROGRESS_END + "\n");
    }



    public void update(double percentage) {
//        System.out.print(PROGRESS_START + INCREMENT + INCREMENT + INCREMENT + "      " + PROGRESS_END + "\r");
//        double progress = Math.min(1, Math.max(0, percentage / 100.0));
//        double whole_width = Math.floor(progress * maxLength);
//        double remainder_width = ((progress * maxLength) % 1);
//        int part_width = (int) Math.floor(remainder_width * 8);
        String[] part_char = {"", INCREMENT_1, INCREMENT_2, INCREMENT_3, INCREMENT_4, INCREMENT_5, INCREMENT_6, INCREMENT_7, INCREMENT_8};
//        StringBuilder str = new StringBuilder(PROGRESS_START);
//        for (int i = 0; i < whole_width; i++) {
//            str.append(INCREMENT_8);
//        }
//        str.append(part_char[part_width]);
//        for (int i = 0; i < (maxLength - whole_width - 1); i++) {
//            str.append(" ");
//        }
//        str.append(PROGRESS_END);
//        System.out.println(str.toString());
        int modal = (int) ((percentage) % 9);
        int progress = (int) (percentage / 9);
        StringBuilder str = new StringBuilder(PROGRESS_START);
        for (int i = 0; i < progress; i++) {
            str.append(INCREMENT_8);
        }
        str.append(part_char[modal]);
        for (int i = 0; i < 9 - modal; i++) {
            str.append('\u2006');
        }
        if (modal >= 6)
            str.append('\u2006');
        if (progress < 70) {
            for (int i = 0; i < maxLength - (progress + 1); i++) {
                str.append('\u2003');
            }
        }
        str.append(PROGRESS_END);
        System.out.print(str.toString() + '\r');
    }
}
