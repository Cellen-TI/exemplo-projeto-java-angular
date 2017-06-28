module.exports = {
  dist: {
    files: [
    {
      expand: true,
      dot: true,
      cwd: 'public',
      dest: 'dist',
      src: '**'
    },
    {
      expand: true,
      dot: true,
      cwd: 'node_modules',
      dest: 'dist/node_modules',
      src: '**'
    }
  ]}
};
