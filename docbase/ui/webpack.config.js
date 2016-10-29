var path = require('path');
var webpack = require('webpack');

module.exports = {
  //Specify default options for the devserver commannd
  devServer: {
   contentBase: "./src"
 },
  //An array of files which will be run at startup
  entry: [
    'babel-polyfill',
    './src/app'
  ],
  //Tells the webpack-dev-server where to serve compiled files from
  //We tell it to build the single file app.js,
  //hide the original from the browser
  output: {
      publicPath: '/',
      filename: 'app.js'
  },
  //Tells webpack to automatically serve us source maps as well
  //so browsers can display the original source in their dev console
  devtool: 'source-map',
  //A list of loaders - npm packages which allow webpack to transform
  //our source
  module: {
    loaders: [
      {
        test: /\.js$/,
        include: path.join(__dirname, 'src'),
        loader: 'babel-loader',
        query: {
          presets: ["es2015", "react"],
        }
      }
    ]
  },
  debug: true
};
