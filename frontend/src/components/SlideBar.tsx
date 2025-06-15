import React from 'react'
import vecteezy_business from '../assets/image/vecteezy_business.png';

const SlideBar = () => {
  return (
    <div
  className="
    bg-blue-700 h-full p-2
    grid grid-rows-2
    place-items-center 
    sm:place-items-start sm:justify-items-start
  "
>
  <label className="text-4xl font-bold text-white p-4 text-center sm:text-left ">
    ToDoList
  </label>

  <img
    src={vecteezy_business}
    alt="Vecteezy 3D"
    width={200}
    height={100}
    className="w-50 mt-auto"
  />
</div>


  )
}

export default SlideBar