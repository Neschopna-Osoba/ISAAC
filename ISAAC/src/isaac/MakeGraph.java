/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isaac;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author petrs
 */
class MakeGraph extends JComponent {

    private ArrayList<String> function;

    MakeGraph(ArrayList<String> text) {
        function = text;
    }

    public void paintComponent(Graphics g) {

        Polygon p1 = new Polygon();
        Polygon p2 = new Polygon();

        int x = getSize().width;
        int y = getSize().height;

        //[0,0] coordinates
        int x0 = x / 2;
        int y0 = y / 2;

        Graphics2D g1 = (Graphics2D) g;

        g1.drawLine(0, y / 2, x, y / 2); //Draw X-Axis
        g1.drawLine(x / 2, 0, x / 2, y); //Draw Y-Axis

        final double scale = 25;

        int goniopos = 0;
        int powpos = 0;
        int logpos = 0;

        boolean sin = false;
        boolean cos = false;
        boolean tg = false;
        boolean cotg = false;
        boolean pow = false;
        boolean sqrt = false;
        boolean log10 = false;
        boolean ln = false;

        for (int i = 0; i < function.size(); i++) {
            if (function.get(i) == "sin") {
                goniopos = i;
                sin = true;
            }
            if (function.get(i) == "cos") {
                goniopos = i;
                cos = true;
            }
            if (function.get(i) == "tg") {
                goniopos = i;
                tg = true;
            }
            if (function.get(i) == "cotg") {
                goniopos = i;
                cotg = true;
            }
            if (function.get(i) == "^") {
                powpos = i;
                pow = true;
            }
            if (function.get(i) == "log10") {
                logpos = i;
                log10 = true;
            }
            if (function.get(i) == "ln") {
                logpos = i;
                ln = true;
            }
            /*  if (function.get(i) == "sqrtsSYMBOL") {
                sqrt = true;
            }   */
        }

        if (sin || cos || tg || cotg) { //Gonio functions

            int gonmult = 0;
            int varmult = 0;
            int gonabs = 0;
            int varabs = 0;

            boolean brackets = false;
            if (function.get(goniopos - 1) == "-") {
                gonmult = -1;
            } else if (function.get(goniopos - 1) == "+") {
                gonmult = 1;
            } else if (function.get(goniopos - 1) == "*") {
                if (function.get(goniopos - 3) == "-") {
                    gonmult = -Integer.valueOf(function.get(goniopos - 2));
                } else {
                    gonmult = Integer.valueOf(function.get(goniopos - 2));
                }
            }

            for (int i = 0; i < function.size(); i++) {

                if (function.get(i) == ")") {
                    brackets = false;
                    try {
                        if (function.get(i + 1) == "-") {
                            gonabs = -Integer.valueOf(function.get(i + 2));
                        } else if (function.get(i + 1) == "+") {
                            gonabs = Integer.valueOf(function.get(i + 2));
                        }

                    } catch (Exception e) {
                        gonabs = 0;
                    }

                }
                if (brackets) {
                    if (function.get(i) == "x") {
                        if (function.get(i - 1) == "(") {
                            varmult = 1;
                        } else if (function.get(i - 1) == "-") {
                            varmult = -1;
                        } else if (function.get(i - 1) == "*") {

                            if (function.get(i - 3) == "-") {
                                varmult = -Integer.valueOf(function.get(i - 2));
                            } else {
                                varmult = Integer.valueOf(function.get(i - 2));
                                System.out.println("zde");
                            }
                        }
                        if (function.get(i + 1) == ")") {
                            varabs = 0;
                        } else if (function.get(i + 1) == "-") {
                            varabs = -Integer.valueOf(function.get(i + 2));
                        } else if (function.get(i + 1) == "+") {
                            varabs = Integer.valueOf(function.get(i + 2));
                        }
                    }

                }
                if (function.get(i) == "(") {
                    brackets = true;
                }
            }

            if (sin) {
                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p1.addPoint(x0 + i, y0 - (int) Math.round(scale * gonmult * Math.sin(varmult * (iScaled + varabs)) + gonabs * scale));

                }

                g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p2.addPoint(x0 - i, y0 + (int) Math.round(scale * gonmult * Math.sin(varmult * (iScaled - varabs)) - gonabs * scale));

                }
                g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);

            } else if (cos) {
                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p1.addPoint(x0 + i, y0 - (int) Math.round(scale * gonmult * Math.cos(varmult * (iScaled + varabs)) + gonabs * scale));
                }

                g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p2.addPoint(x0 - i, y0 - (int) Math.round(scale * gonmult * Math.cos(varmult * (iScaled - varabs)) - gonabs * scale));

                }
                g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
            } else if (tg) {

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p1.addPoint(x0 + i, y0 - (int) Math.round(scale * gonmult * Math.tan(varmult * (iScaled + varabs)) + gonabs * scale));

                }

                g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p2.addPoint(x0 - i, y0 + (int) Math.round(scale * gonmult * Math.tan(varmult * (iScaled - varabs)) - gonabs * scale));

                }
                g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
            } else if (cotg) {

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p1.addPoint(x0 + i, y0 - (int) Math.round(scale * gonmult * (Math.cos(varmult * (iScaled - varabs)) / Math.sin(varmult * (iScaled - varabs))) + gonabs * scale));

                }

                g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p2.addPoint(x0 - i, y0 + (int) Math.round(scale * gonmult * (Math.cos(varmult * (iScaled - varabs)) / Math.sin(varmult * (iScaled - varabs))) - gonabs * scale));

                }
                g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
            }
        } else if (pow) {
            int powvalue = 0;
            int varmult = 0;
            int varabs = 0;
            int powmult = 0;
            int powabs = 0;

            boolean brackets = false;

            powvalue = Integer.valueOf(function.get(powpos + 1));

            try {
                if (function.get(powpos + 2) == "-") {
                    powabs = -Integer.valueOf(function.get(powpos + 3));
                } else if (function.get(powpos + 2) == "+") {
                    powabs = Integer.valueOf(function.get(powpos + 3));
                }
            } catch (Exception e) {
                powabs = 0;
            }

            for (int i = 0; i < function.size(); i++) {
                if (function.get(i) == "(") {
                    if (function.get(i - 1) == "-") {
                        powmult = -1;
                    } else if (function.get(i - 1) == "+") {
                        powmult = 1;
                    } else if (function.get(i - 1) == "*") {
                        if (function.get(i - 3) == "-") {
                            powmult = -Integer.valueOf(function.get(i - 2));
                        } else {
                            powmult = Integer.valueOf(function.get(i - 2));
                        }
                    }
                } else if (function.get(i) == "x") {
                    if (function.get(i - 1) == "(") {
                        varmult = 1;
                    } else if (function.get(i - 1) == "-") {
                        varmult = -1;
                    } else if (function.get(i - 1) == "*") {
                        if (function.get(i - 3) == "-") {
                            varmult = -Integer.valueOf(function.get(i - 2));
                        } else {
                            varmult = Integer.valueOf(function.get(i - 2));
                        }
                    }
                    if (function.get(i + 1) == ")") {
                        varabs = 0;
                    } else if (function.get(i + 1) == "-") {
                        varabs = -Integer.valueOf(function.get(i + 2));
                    } else {
                        varabs = Integer.valueOf(function.get(i + 2));
                    }

                }
            }

            for (int i = 0; i <= x0; i++) {
                double iScaled = i / scale;
                p1.addPoint(x0 + i, y0 - (int) Math.round(scale * powmult * (Math.pow((varmult * (iScaled + varabs)), powvalue)) + powabs * scale));
            }

            g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

            for (int i = 0; i <= x0; i++) {
                double iScaled = i / scale;
                p2.addPoint(x0 - i, y0 - (int) Math.round(scale * powmult * (Math.pow((varmult * (-iScaled + varabs)), powvalue)) + powabs * scale));
            }

            g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);

        } else if (sqrt) {

        } else if (log10 || ln) {
            System.out.println("test");
            int logmult = 0;
            int varmult = 0;
            int logabs = 0;
            int varabs = 0;

            boolean brackets = false;
            if (function.get(logpos - 1) == "-") {
                logmult = -1;
            } else if (function.get(logpos - 1) == "+") {
                logmult = 1;
            } else if (function.get(logpos - 1) == "*") {
                if (function.get(logpos - 3) == "-") {
                    logmult = -Integer.valueOf(function.get(logpos - 2));
                } else {
                    logmult = Integer.valueOf(function.get(logpos - 2));
                }
            }

            for (int i = 0; i < function.size(); i++) {

                if (function.get(i) == ")") {
                    brackets = false;
                    try {
                        if (function.get(i + 1) == "-") {
                            logabs = -Integer.valueOf(function.get(i + 2));
                        } else if (function.get(i + 1) == "+") {
                            logabs = Integer.valueOf(function.get(i + 2));
                        }

                    } catch (Exception e) {
                        logabs = 0;
                    }

                }
                if (brackets) {
                    if (function.get(i) == "x") {
                        if (function.get(i - 1) == "(") {
                            varmult = 1;
                        } else if (function.get(i - 1) == "-") {
                            varmult = -1;
                        } else if (function.get(i - 1) == "*") {

                            if (function.get(i - 3) == "-") {
                                varmult = -Integer.valueOf(function.get(i - 2));
                            } else {
                                varmult = Integer.valueOf(function.get(i - 2));
                                System.out.println("zde");
                            }
                        }
                        if (function.get(i + 1) == ")") {
                            varabs = 0;
                        } else if (function.get(i + 1) == "-") {
                            varabs = -Integer.valueOf(function.get(i + 2));
                        } else if (function.get(i + 1) == "+") {
                            varabs = Integer.valueOf(function.get(i + 2));
                        }
                    }

                }
                if (function.get(i) == "(") {
                    brackets = true;
                }
            }

            if (log10) {
                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p1.addPoint(x0 + i, y0 - (int) Math.round(scale * logmult * Math.log10(varmult * (iScaled + varabs)) + logabs * scale));

                }

                g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p2.addPoint(x0 - i, y0 + (int) Math.round(scale * logmult * Math.log10(varmult * (iScaled - varabs)) - logabs * scale));

                }
                g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);

            } else if (ln) {

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p1.addPoint(x0 + i, y0 - (int) Math.round(scale * logmult * Math.log(varmult * (iScaled + varabs)) + logabs * scale));

                }

                g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

                for (int i = 0; i <= x0; i++) {
                    double iScaled = i / scale;
                    p2.addPoint(x0 - i, y0 + (int) Math.round(scale * logmult * Math.log(varmult * (iScaled - varabs)) - logabs * scale));

                }
                g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
            }

        } else { //LINEAR
            int varmult = 0;
            int varabs = 0;

            for (int i = 0; i < function.size(); i++) {

                if (function.get(i) == "x") {
                    if (function.get(i - 1) == "-") {
                        varmult = -1;
                    } else if (function.get(i - 1) == "+") {
                        varmult = 1;
                    } else if (function.get(i - 1) == "*") {
                        if (function.get(i - 3) == "-") {
                            varmult = -Integer.valueOf(function.get(i - 2));
                        } else {
                            varmult = Integer.valueOf(function.get(i - 2));
                        }
                    }
                    try {
                        if (function.get(i + 1) == "-") {
                            varabs = -Integer.valueOf(function.get(i + 2));
                        } else {
                            varabs = Integer.valueOf(function.get(i + 2));
                        }
                    } catch (Exception e) {
                        varabs = 0;
                    }

                }

            }

            for (int i = 0; i <= x0; i++) {
                double iScaled = i / scale;
                p1.addPoint(x0 + i, y0 - (int) Math.round(scale * varmult * (iScaled - varabs)));
            }

            g1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);

            for (int i = 0; i <= x0; i++) {
                double iScaled = i / scale;
                p2.addPoint(x0 - i, y0 - (int) Math.round(scale * varmult * (-iScaled - varabs)));
            }

            g1.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);

        }
    }
}
