package me.creepinson.tubesplus.util;

class GravityConst {
  public static final int[] matirxRoatUpToDownI = new int[] { 1, 0, 0, 0, 1, 0, 0, 0, 1 };
  
  public static final int[] matirxRoatDownTOupI = new int[] { 1, 0, 0, 0, -1, 0, 0, 0, -1 };
  
  public static final int[] matirxRoatEastTOwestI = new int[] { 0, -1, 0, 1, 0, 0, 0, 0, 1 };
  
  public static final int[] matirxRoatWestTOeastI = new int[] { 0, 1, 0, -1, 0, 0, 0, 0, 1 };
  
  public static final int[] matirxRoatNorthTOsouthI = new int[] { 1, 0, 0, 0, 0, -1, 0, 1, 0 };
  
  public static final int[] matirxRoatSouthTOnorthI = new int[] { 1, 0, 0, 0, 0, 1, 0, -1, 0 };
  
  public static final double[] matirxRoatUpToDownD = new double[] { 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D };
  
  public static final double[] matirxRoatDownTOupD = new double[] { 1.0D, 0.0D, 0.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, -1.0D };
  
  public static final double[] matirxRoatEastTOwestD = new double[] { 0.0D, -1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D };
  
  public static final double[] matirxRoatWestTOeastD = new double[] { 0.0D, 1.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D };
  
  public static final double[] matirxRoatNorthTOsouthD = new double[] { 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, -1.0D, 0.0D, 1.0D, 0.0D };
  
  public static final double[] matirxRoatSouthTOnorthD = new double[] { 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
  
  public static final int[] forgeSideRotUpToDown = new int[] { 0, 1, 2, 3, 4, 5 };
  
  public static final int[] forgeSideRotDownTOup = new int[] { 1, 0, 3, 2, 4, 5 };
  
  public static final int[] forgeSideRotEastTOwest = new int[] { 5, 4, 2, 3, 0, 1 };
  
  public static final int[] forgeSideRotWestTOeast = new int[] { 4, 5, 2, 3, 1, 0 };
  
  public static final int[] forgeSideRotNorthTOsouth = new int[] { 2, 3, 1, 0, 4, 5 };
  
  public static final int[] forgeSideRotSouthTOnorth = new int[] { 3, 2, 0, 1, 4, 5 };
}
