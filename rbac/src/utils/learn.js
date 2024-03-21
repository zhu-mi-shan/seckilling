let arr = [{
  a: 1,
  b: 4,
},
{
  a: 3,
  b: 5,
}]
let re = arr.filter(value => {
    value.c = 7
    return true
})
console.log(re)