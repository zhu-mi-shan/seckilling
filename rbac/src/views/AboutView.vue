<template>
  <div class="about">
    <div class="header">header</div>
    <div class="main">
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
      <div>main</div>
    </div>
    <div class="foot">foot</div>
  </div>
</template>

<style lang="scss">
// justify-content : 主轴(横向)
// align-items: 纵轴(竖向)
// align-content: 多个主轴的竖向排列
// align-self: 改变自己的纵轴排列

// flex auto = 1 1 auto
// flex: 0 = 0 1 0
// flex: 1 = flex-grow: 1, flex-shrink: 1, flex-basis: 0;
// flex: 2 = 2 1 0
.about{
  display: flex;
  flex-direction: column;
  height: 100vh;
  .header{
    height: 15vh;
    background-color: red;
  }
  .main{
    flex: 1;
    background-color: yellow;
    overflow: auto;
    display: flex;
    flex-direction: column;
  }
  .foot{
    height: 10vh;
    background-color: green;
  }
}
</style>
