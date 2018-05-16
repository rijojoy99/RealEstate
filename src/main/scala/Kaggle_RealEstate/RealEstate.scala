package Kaggle_RealEstate


object RealEstate {

  def main(args: Array[String]): Unit = {

    println("Start of initial processing")

    val spark = SparkSession.builder().appName("RealEstate").master("local[4]").getOrCreate()

    var trainDf = spark.read.option("header", true).option("inferSchema", true).csv("src/test/repository/train.csv")

    var testDf = spark.read.option("header", true).option("inferSchema", true).csv("src/test/repository/test.csv")

    trainDf.show(10)

    trainDf.columns.map { x =>
      trainDf = trainDf.withColumn(x, when(trim(col(x)).equalTo(lit("NA")), "").otherwise(col(x)))
    }
    val colArray = trainDf.columns


    trainDf.show(10)
    trainDf.stat
    println("Stat \n" + trainDf.stat.freqItems(colArray.filter( f => !f.startsWith("Id")).toSeq).show(10,false))
    sys.exit(0)

    println(trainDf.schema)
    //    println(trainDf.stat.sampleBy())

    val getCols = trainDf.schema.fieldNames
    println(getCols)

    //    sys.exit()


    //define the feature columns to put in the feature vector
    val featureCols = colArray

    val strIndexer = new StringIndexer().setInputCol("features").setOutputCol("features_Indexed")
    //set the input and output column names
    val assembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")
    //return a dataframe with all of the  feature columns in  a vector column
    val df2 = assembler.transform(trainDf)
    // the transform method produced a new column: features.
    df2.show

    //  Create a label column with the StringIndexer**
    val labelIndexer = new StringIndexer().setInputCol("SalePrice").setOutputCol("label")
    val df3 = labelIndexer.fit(df2).transform(df2)
    // the  transform method produced a new column: label.**
    df3.show
    val splitSeed = 5043
    val Array(trainingData, testData) = df3.randomSplit(Array(0.7, 0.3), splitSeed)

    val treeStrategy = ""
    val numTrees = 10
    val featureSubsetStrategy = "auto"
    // create the classifier,  set parameters for training**
    val rf = new RandomForestRegressor()
      .setNumTrees(40)
      .setMaxBins(30)
    //          trainClassifier(trainingData,treeStrategy, numTrees, featureSubsetStrategy, seed = 12345)
  }

  //  use logistic regression to train (fit) the model with the training data**


}
