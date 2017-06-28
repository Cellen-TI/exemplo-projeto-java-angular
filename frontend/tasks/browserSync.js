module.exports = {
  server: {
    options: {
      files: [
        'public/**'
      ],
      server: {
        baseDir: [
          'public'
        ],
        routes: {
          '/node_modules': 'node_modules'
        },
        middleware: function (req, res, next) {
          res.setHeader('Access-Control-Allow-Origin', '*');
          next();
        }
      },
      port: 9000
    }
  }
};
