var path = require('path');
//To create extra .css files instead of <style>... we need this:
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
    //Specify default options for the devserver command
    devServer: {
        contentBase: "./src"
    },
    //An array of files which will be run at startup
    entry: [
        'babel-polyfill',
        './src/app'
    ],
    //Tells the webpack-dev-server where to serve compiled files from
    //We tell it to build the single file app.bundle.js,
    output: {
        publicPath: '/',
        filename: 'app.bundle.js'
    },
    //Tells webpack to automatically serve us source maps as well
    //so browsers can display the original source in their dev console
    devtool: 'source-map',
    //A list of loaders - npm packages which allow webpack to transform
    //our source
    module: {
        loaders: [
            //Babel
            {
                test: /\.js$/,
                include: path.join(__dirname, 'src'),
                loader: 'babel-loader',
                query: {
                    presets: ["es2015", "react"],
                }
            },
            //Sass
            {
                test: /\.scss$/,
                loader: ExtractTextPlugin.extract('css?sourceMap!sass?sourceMap')
            }
        ]
    },
    plugins: [
        new ExtractTextPlugin('src/style.css', {
            allChunks: true
        })
    ],
    debug: true
};
