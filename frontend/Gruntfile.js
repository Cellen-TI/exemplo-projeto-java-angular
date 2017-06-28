const path = require('path');
const time = require('time-grunt');
const load = require('load-grunt-config');

module.exports = function(grunt) {
  tasks = path.join(process.cwd(), 'tasks');

  if (!grunt.file.exists('.env.dev')) {
    grunt.file.copy('.env.prod', '.env.dev');
  }

  // Time how long tasks take. Can help when optimizing build times
  time(grunt);

  // Automatically load required grunt tasks
  load(grunt, {
    configPath: tasks,
    init: true
  });
};
