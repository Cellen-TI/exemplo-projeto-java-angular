var grunt = require('grunt');

module.exports = {
  dist: {
    options: {
      name: 'app.constants',
      dest: 'public/app/app.constants.js',
      values: {
        CONST: grunt.file.readJSON('.env.prod')
      }
    }
  },
  server: {
    options: {
      name: 'app.constants',
      dest: 'public/app/app.constants.js',
      values: {
        CONST: grunt.file.readJSON('.env.dev')
      }
    }
  }
};
