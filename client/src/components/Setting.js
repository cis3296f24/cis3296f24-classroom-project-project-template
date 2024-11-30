function Setting() {
  return (
    <>
      <div className="container w-full flex flex-wrap mx-auto px-2 pt-8 lg:pt-16 mt-16">
        <div className="w-full lg:w-1/5 px-6 text-xl text-gray-800 leading-normal">
          <p className="text-base font-bold py-2 lg:pb-6 text-gray-700">Menu</p>
          <div className="block lg:hidden sticky inset-0">
            <button
              id="menu-toggle"
              className="flex w-full justify-end px-3 py-3 bg-white lg:bg-transparent border rounded border-gray-600 hover:border-yellow-600 appearance-none focus:outline-none"
            >
              <svg
                className="fill-current h-3 float-right"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" />
              </svg>
            </button>
          </div>
          <div
            className="w-full sticky inset-0 hidden max-h-64 lg:h-auto overflow-x-hidden overflow-y-auto lg:overflow-y-hidden lg:block mt-0 my-2 lg:my-0 border border-gray-400 lg:border-transparent bg-white shadow lg:shadow-none lg:bg-transparent z-20"
            style={{ top: "6em" }}
            id="menu-content"
          >
            <ul className="list-reset py-2 md:py-0">
              <li className="py-1 md:my-2 hover:bg-yellow-100 lg:hover:bg-transparent border-l-4 border-transparent font-bold border-yellow-600">
                <a
                  href="#section1"
                  className="block pl-4 align-middle text-gray-700 no-underline hover:text-yellow-600"
                >
                  <span className="pb-1 md:pb-0 text-sm">Change Username</span>
                </a>
              </li>
              <li className="py-1 md:my-2 hover:bg-yellow-100 lg:hover:bg-transparent border-l-4 border-transparent">
                <a
                  href="#section2"
                  className="block pl-4 align-middle text-gray-700 no-underline hover:text-yellow-600"
                >
                  <span className="pb-1 md:pb-0 text-sm">Change Bio</span>
                </a>
              </li>
              <li className="py-1 md:my-2 hover:bg-yellow-100 lg:hover:bg-transparent border-l-4 border-transparent">
                <a
                  href="#section3"
                  className="block pl-4 align-middle text-gray-700 no-underline hover:text-yellow-600"
                >
                  <span className="pb-1 md:pb-0 text-sm">Change Email</span>
                </a>
              </li>
              <li className="py-1 md:my-2 hover:bg-yellow-100 lg:hover:bg-transparent border-l-4 border-transparent">
                <a
                  href="#section4"
                  className="block pl-4 align-middle text-gray-700 no-underline hover:text-yellow-600"
                >
                  <span className="pb-1 md:pb-0 text-sm">Change Avatar</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
        
        <section className="w-full lg:w-4/5">
          <h1 className="flex items-center font-sans font-bold break-normal text-gray-700 px-2 text-xl mt-12 lg:mt-0 md:text-2xl">
            User Settings
          </h1>
          
          <hr className="bg-gray-300 my-12" />
         
          <h2
            id="section1"
            className="font-sans font-bold break-normal text-gray-700 px-2 pb-8 text-xl"
          >
            Change Username
          </h2>
          
          <div className="p-8 mt-6 lg:mt-0 leading-normal rounded shadow">
            <div className="md:flex mb-6">
              <div className="md:w-1/3">
                <label
                  className="block text-gray-600 font-bold md:text-left mb-3 md:mb-0 pr-4"
                  htmlFor="my-textfield"
                >
                  New Username
                </label>
              </div>
              <div className="md:w-2/3">
                <input
                  className="form-input block w-full focus:bg-neutral-600 bg-neutral-700"
                  id="my-textfield"
                  type="text"
                  defaultValue=""
                />
                <p className="py-2 text-sm text-gray-600">
                  Please type into the input box and hit save to change your username
                </p>
              </div>
              <div className="md:flex md:items-center">
                <div className="md:w-1/3" />
                <div className="md:w-2/3">
                  <button
                    className="shadow bg-yellow-700 hover:bg-yellow-500 focus:shadow-outline focus:outline-none text-white font-bold py-2 px-4 rounded"
                    type="button"
                  >
                    Save
                  </button>
                </div>
              </div>
            </div>
          </div>
          
          <hr className="bg-gray-300 my-12" />
         
          <h2 className="font-sans font-bold break-normal text-gray-700 px-2 pb-8 text-xl">
            Bio
          </h2>
          
          <div id="section2" className="p-8 mt-6 lg:mt-0 rounded shadow">
            <form>
              <div className="md:flex mb-6">
                <div className="md:w-1/3">
                  <label
                    className="block text-gray-600 font-bold md:text-left mb-3 md:mb-0 pr-4"
                    htmlFor="my-textarea"
                  >
                    Edit Bio
                  </label>
                </div>
                <div className="md:w-2/3">
                  <textarea
                    className="form-textarea block w-full focus:bg-neutral-600 bg-neutral-700"
                    id="my-textarea"
                    value=""
                    rows={8}
                    defaultValue={""}
                  />
                  <p className="py-2 text-sm text-gray-600">
                    Type into the text area and hit save to add a bio to your profile
                  </p>
                </div>
              </div>
              <div className="md:flex md:items-center">
                <div className="md:w-1/3" />
                <div className="md:w-2/3">
                  <button
                    className="shadow bg-yellow-700 hover:bg-yellow-500 focus:shadow-outline focus:outline-none text-white font-bold py-2 px-4 rounded"
                    type="button"
                  >
                    Save
                  </button>
                </div>
              </div>
            </form>
          </div>
          
          <hr className="bg-gray-300 my-12" />
          
          <h2 className="font-sans font-bold break-normal text-gray-700 px-2 pb-8 text-xl">
            Change Email
          </h2>
          
          <div className="p-8 mt-6 lg:mt-0 leading-normal rounded shadow">
            <div className="md:flex mb-6">
              <div className="md:w-1/3">
                <label
                  className="block text-gray-600 font-bold md:text-left mb-3 md:mb-0 pr-4"
                  htmlFor="my-textfield"
                >
                  Change Email
                </label>
              </div>
              <div className="md:w-2/3">
                <input
                  className="form-input block w-full focus:bg-neutral-600 bg-neutral-700"
                  id="my-textfield"
                  type="text"
                  defaultValue=""
                />
                <p className="py-2 text-sm text-gray-600">
                  Please type into the input box and hit save to change your email
                </p>
              </div>
              <div className="md:flex md:items-center">
                <div className="md:w-1/3" />
                <div className="md:w-2/3">
                  <button
                    className="shadow bg-yellow-700 hover:bg-yellow-500 focus:shadow-outline focus:outline-none text-white font-bold py-2 px-4 rounded"
                    type="button"
                  >
                    Save
                  </button>
                </div>
              </div>
            </div>
          </div>
          
          <hr className="bg-gray-300 my-12" />
         
          <h2 className="font-sans font-bold break-normal text-gray-700 px-2 pb-8 text-xl">
            Change Avatar
          </h2>
          
          <div className="p-8 mt-6 lg:mt-0 leading-normal rounded shadow">
            <div className="md:flex mb-6">
              <div className="md:w-1/3">
                <label
                  className="block text-gray-600 font-bold md:text-left mb-3 md:mb-0 pr-4"
                  htmlFor="my-textfield"
                >
                  Image URL
                </label>
              </div>
              <div className="md:w-2/3">
                <input
                  className="form-input block w-full focus:bg-neutral-600 bg-neutral-700"
                  id="my-textfield"
                  type="text"
                  defaultValue=""
                />
                <p className="py-2 text-sm text-gray-600">
                  Please type into the input box and hit save to change your avatar
                </p>
              </div>
              <div className="md:flex md:items-center">
                <div className="md:w-1/3" />
                <div className="md:w-2/3">
                  <button
                    className="shadow bg-yellow-700 hover:bg-yellow-500 focus:shadow-outline focus:outline-none text-white font-bold py-2 px-4 rounded"
                    type="button"
                  >
                    Save
                  </button>
                </div>
              </div>
            </div>
          </div>

        </section>

        <div className="w-full lg:w-4/5 lg:ml-auto text-base md:text-sm text-gray-600 px-4 py-24 mb-12">
          <span className="text-base text-yellow-600 font-bold">&lt;</span>{" "}
          <a
            href="#"
            className="text-base md:text-sm text-yellow-600 font-bold no-underline hover:underline"
          >
            Back link
          </a>
        </div>
      </div>
    </>


  );
}



export default Setting;