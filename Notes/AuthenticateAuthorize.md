> *Topics --> Authentication, Authorization, . . .*\
> *Link to Codes --> [Codes](../Codes/AuthenticateAuthorize.java)*



### Authentication ("Who are you?")
- Authentication is the process of verifying the identity of a user or system.
- *Methods*
  - **Something you know** --> A password or a PIN.
  - **Something you have** --> A key card, a security token, or your phone (for a one-time code).
  - **Something you are** --> Biometrics like a fingerprint, facial scan, or voice recognition.

- *Authentication Factors*
  - **Single-Factor Authentication (SFA)** --> This is the most common and least secure method. It relies on only one thing, typically your password (**something you know**).
  - **Two-Factor Authentication (2FA)** --> This is much more secure. It requires you to provide two different factors. For example, your password (**something you know**) and a one-time code sent to your phone (**something you have**).
  - **Multi-Factor Authentication (MFA)** --> This uses two or more factors, adding layers of security. It might combine your password, a code from your phone, and a fingerprint scan (**something you are**).


---

### Authorization ("What are you allowed to do?")
- Authorization is the process of determining whether a user or system has permission to access a resource or perform an action. (usually happens after successfull Authentication)

- *Authorization Models permission-based*
  - Once you're authenticated, the system needs a model to decide what you're allowed to do.

  - **Role-Based Access Control (RBAC)** --> This is the most common model. Your permissions are based on your assigned "role." For example, in a company's system, you might have roles like "Administrator," "Manager," or "Employee." An **Administrator** can add or delete users, while an Employee can only view their own profile.

  - **Attribute-Based Access Control (ABAC)** --> This is a more dynamic and fine-grained model. Access is granted based on attributes of the user, the resource being accessed, and the environment. For example, a rule might state: "Allow users in the 'Marketing' department (user attribute) to access the 'Social Media Dashboard' (resource attribute) only during business hours (environment attribute)."

  - **Discretionary Access Control (DAC)** --> This is a flexible model where the owner of a resource decides who can access it. For example, if you own a document, you can choose to share it with specific people or keep it private.

  - **Mandatory Access Control (MAC)** --> This is a stricter model where access is controlled by a central authority based on security policies. For example, in a military system, access to documents might be classified as "Top Secret," and only users with the appropriate clearance can access them.

  - **Rule-Based Access Control (RuBAC)** --> Access is granted or denied based on a set of pre-defined rules. 

